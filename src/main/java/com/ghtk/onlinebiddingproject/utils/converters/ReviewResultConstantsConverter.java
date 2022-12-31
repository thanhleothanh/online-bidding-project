package com.ghtk.onlinebiddingproject.utils.converters;


import com.ghtk.onlinebiddingproject.constants.ReviewResultConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReviewResultConstantsConverter implements AttributeConverter<ReviewResultConstants, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReviewResultConstants result) {
        if (result == null) {
            return null;
        }
        return result.getResult();
    }

    @Override
    public ReviewResultConstants convertToEntityAttribute(Integer result) {
        if (result == null) {
            return null;
        }

        return Stream.of(ReviewResultConstants.values())
                .filter(value -> value.getResult() == result)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
