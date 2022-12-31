package com.ghtk.onlinebiddingproject.utils.converters;


import com.ghtk.onlinebiddingproject.constants.ReportResultConstants;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReportResultConstantsConverter implements AttributeConverter<ReportResultConstants, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReportResultConstants result) {
        if (result == null) {
            return null;
        }
        return result.getResult();
    }

    @Override
    public ReportResultConstants convertToEntityAttribute(Integer result) {
        if (result == null) {
            return null;
        }

        return Stream.of(ReportResultConstants.values())
                .filter(value -> value.getResult() == result)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
