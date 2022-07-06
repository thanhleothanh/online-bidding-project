package com.ghtk.onlinebiddingproject.utils.converters;


import com.ghtk.onlinebiddingproject.constants.UserStatusConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserStatusConstantsConverter implements AttributeConverter<UserStatusConstants, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserStatusConstants status) {
        if (status == null) return null;
        return status.getStatus();
    }

    @Override
    public UserStatusConstants convertToEntityAttribute(Integer status) {
        if (status == null) {
            return null;
        }

        return Stream.of(UserStatusConstants.values())
                .filter(value -> value.getStatus() == status)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
