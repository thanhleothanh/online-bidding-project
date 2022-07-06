package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.constants.AuctionStatusConstants;
import com.ghtk.onlinebiddingproject.exceptions.NotFoundException;
import com.ghtk.onlinebiddingproject.models.dtos.AuctionDto;
import com.ghtk.onlinebiddingproject.models.entities.Auction;
import com.ghtk.onlinebiddingproject.models.entities.Item;
import com.ghtk.onlinebiddingproject.models.entities.User;
import com.ghtk.onlinebiddingproject.models.entities.Winner;
import com.ghtk.onlinebiddingproject.models.responses.AuctionPagingResponse;
import com.ghtk.onlinebiddingproject.repositories.AuctionRepository;
import com.ghtk.onlinebiddingproject.repositories.UserRepository;
import com.ghtk.onlinebiddingproject.security.UserDetailsImpl;
import com.ghtk.onlinebiddingproject.services.AuctionService;
import com.ghtk.onlinebiddingproject.utils.DtoToEntityUtils;
import com.ghtk.onlinebiddingproject.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public AuctionPagingResponse get(Specification<Auction> spec, HttpHeaders headers, Sort sort) {
        if (PaginationUtils.isPaginationRequested(headers)) {
            return helperGet(spec, PaginationUtils.buildPageRequest(headers, sort));
        } else {
            List<Auction> productEntities = helperGet(spec, sort);
            return new AuctionPagingResponse(productEntities.size(), 0, 0, 0, productEntities);
        }
    }

    @Override
    public Auction getById(Integer id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy auction với id này!"));
    }

    @Override
    public Auction save(Auction auction) {
        //lấy current logged in user để làm user tạo bài đấu giá
        UserDetailsImpl
                userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User(userDetails.getId());
        auction.setUser(user);
        return auctionRepository.save(auction);
    }

    @Override
    public Auction put(AuctionDto auctionDto, Auction auction) {
        if (auction.getStatus().equals(AuctionStatusConstants.PENDING) || auction.getStatus().equals(AuctionStatusConstants.DRAFT)) {
            //dùng beanUtils để copy những giá trị không null từ dto client gửi lên sang existingAuction entity rồi update
            DtoToEntityUtils.copyNonNullProperties(auctionDto, auction);
            if (auctionDto.getItem() != null) {
                auction.setItem(new Item(auctionDto.getItem().getId()));
            }
            if (auctionDto.getWinner() != null) {
                auction.setWinner(new Winner(auctionDto.getWinner().getId()));
            }
            return auctionRepository.save(auction);
        } else throw new AccessDeniedException("Không thể thực hiện sửa bài đấu giá khi đã và đang đấu giá!");
    }

    @Override
    public void deleteById(Integer id) {
        Auction auction = getById(id);
        if (auction.getStatus().equals(AuctionStatusConstants.DRAFT) || auction.getStatus().equals(AuctionStatusConstants.PENDING))
            auctionRepository.delete(auction);
        else throw new AccessDeniedException("Không thể thực hiện xoá bài đấu giá khi đã và đang đấu giá!");
    }

    /**
     * helper methods
     */
    public List<Auction> helperGet(Specification<Auction> spec, Sort sort) {
        return auctionRepository.findAll(spec, sort);
    }

    public AuctionPagingResponse helperGet(Specification<Auction> spec, Pageable pageable) {
        Page<Auction> page = auctionRepository.findAll(spec, pageable);
        List<Auction> productEntities = page.getContent();
        return new AuctionPagingResponse((int) page.getTotalElements(), page.getNumber(), page.getNumberOfElements(), page.getTotalPages(), productEntities);
    }
}
