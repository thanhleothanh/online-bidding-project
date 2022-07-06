package com.ghtk.onlinebiddingproject.utils.converters;


import com.ghtk.onlinebiddingproject.constants.ReportStatusConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReportStatusConstantsConverter implements AttributeConverter<ReportStatusConstants, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReportStatusConstants status) {
        if (status == null) return null;
        return status.getStatus();
    }

    @Override
    public ReportStatusConstants convertToEntityAttribute(Integer status) {
        if (status == null) {
            return null;
        }

        return Stream.of(ReportStatusConstants.values())
                .filter(value -> value.getStatus() == status)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
