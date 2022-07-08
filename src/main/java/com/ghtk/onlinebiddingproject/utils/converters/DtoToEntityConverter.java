package com.ghtk.onlinebiddingproject.utils.converters;

import com.ghtk.onlinebiddingproject.models.dtos.AuctionDto;
import com.ghtk.onlinebiddingproject.models.dtos.ItemImageDto;
import com.ghtk.onlinebiddingproject.models.entities.Auction;
import com.ghtk.onlinebiddingproject.models.entities.Bid;
import com.ghtk.onlinebiddingproject.models.entities.Item;
import com.ghtk.onlinebiddingproject.models.entities.ItemImage;
import com.ghtk.onlinebiddingproject.models.requests.AuctionRequestDto;
import com.ghtk.onlinebiddingproject.models.requests.BidRequestDto;
import com.ghtk.onlinebiddingproject.models.requests.ItemRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Auction convertToEntity(AuctionDto auctionDto) throws ParseException {
        return modelMapper.map(auctionDto, Auction.class);
    }

    public Auction convertToEntity(AuctionRequestDto auctionDto) throws ParseException {
        return modelMapper.map(auctionDto, Auction.class);
    }

    public Item convertToEntity(ItemRequestDto itemDto) throws ParseException {
        return modelMapper.map(itemDto, Item.class);
    }


    public ItemImage convertToEntity(ItemImageDto itemImageDto) throws ParseException {
        return modelMapper.map(itemImageDto, ItemImage.class);
    }

    public Bid convertToEntity(BidRequestDto bidDto) throws ParseException {
        return modelMapper.map(bidDto, Bid.class);
    }


}
