package com.ghtk.onlinebiddingproject.utils.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter
public class LocalDateTimeConverter implements AttributeConverter<String, LocalDateTime> {
    @Override
    public LocalDateTime convertToDatabaseColumn(String s) {
        return LocalDateTime.parse(
                s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String convertToEntityAttribute(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }
}
