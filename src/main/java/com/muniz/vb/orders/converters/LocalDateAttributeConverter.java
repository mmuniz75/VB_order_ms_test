
package com.muniz.vb.orders.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp> {
	
    @Override
    public Timestamp convertToDatabaseColumn(LocalDate date) {
    	return (date == null ? null : Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0,0))));
    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp sqlTimestamp) {
    	return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime().toLocalDate());
    }
}