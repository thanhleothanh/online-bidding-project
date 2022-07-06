package com.ghtk.onlinebiddingproject.utils.converters;

import com.ghtk.onlinebiddingproject.models.dtos.AuctionDto;
import com.ghtk.onlinebiddingproject.models.dtos.ItemDto;
import com.ghtk.onlinebiddingproject.models.entities.Auction;
import com.ghtk.onlinebiddingproject.models.entities.Item;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityConverter {
    @Autowired
    ModelMapper modelMapper;

    public Auction convertToEntity(AuctionDto auctionDto) throws ParseException {
        return modelMapper.map(auctionDto, Auction.class);
    }

    public Item convertToEntity(ItemDto itemDto) throws ParseException {
        return modelMapper.map(itemDto, Item.class);
    }


}
