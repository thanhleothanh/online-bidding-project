package com.ghtk.onlinebiddingproject.utils.converters;


import com.ghtk.onlinebiddingproject.constants.AuctionStatusConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
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
