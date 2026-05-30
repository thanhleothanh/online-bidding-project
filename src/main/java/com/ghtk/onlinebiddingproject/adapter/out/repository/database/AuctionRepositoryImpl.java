package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.AuctionMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.AuctionEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.AuctionJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionQuery;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuctionRepositoryImpl implements AuctionRepository {

    private final AuctionJpaRepository auctionJpaRepository;

    public AuctionRepositoryImpl(AuctionJpaRepository auctionJpaRepository) {
        this.auctionJpaRepository = auctionJpaRepository;
    }

    @Override
    public Optional<Auction> findById(Integer id) {
        return auctionJpaRepository.findById(id).map(AuctionMapper::toDomain);
    }

    @Override
    @Transactional
    public Auction save(Auction auction) {
        AuctionEntity entity = AuctionMapper.toEntity(auction);
        AuctionEntity saved = auctionJpaRepository.save(entity);
        return AuctionMapper.toDomain(saved);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        auctionJpaRepository.deleteById(id);
    }

    @Override
    public List<Auction> find(AuctionQuery query) {
        Specification<AuctionEntity> spec = buildSpecification(query);
        return auctionJpaRepository.findAll(spec).stream()
                .map(AuctionMapper::toDomain)
                .toList();
    }

    @Override
    public List<Auction> findPaged(AuctionQuery query, int page, int size) {
        Specification<AuctionEntity> spec = buildSpecification(query);
        return auctionJpaRepository.findAll(spec, PageRequest.of(page, size)).stream()
                .map(AuctionMapper::toDomain)
                .toList();
    }

    private Specification<AuctionEntity> buildSpecification(AuctionQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query.getOwnerId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("ownerId"), query.getOwnerId()));
            }
            if (query.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), query.getStatus()));
            }
            if (query.getDescription() != null && !query.getDescription().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),
                        "%" + query.getDescription().toLowerCase() + "%"
                ));
            }
            if (query.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoryId"), query.getCategoryId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
