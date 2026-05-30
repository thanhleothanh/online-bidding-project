package com.ghtk.onlinebiddingproject.utils.converters;


import com.ghtk.onlinebiddingproject.constants.AuctionStatusConstants;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AuctionStatusConstantsConverter implements AttributeConverter<AuctionStatusConstants, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AuctionStatusConstants status) {
        if (status == null) return null;
        return status.getStatus();
    }

    @Override
    public AuctionStatusConstants convertToEntityAttribute(Integer status) {
        if (status == null) {
            return null;
        }

        return Stream.of(AuctionStatusConstants.values())
                .filter(value -> value.getStatus() == status)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
