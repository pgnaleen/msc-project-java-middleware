package com.ubs.sis.admin.master.converter;

import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BusinessEntityConverter implements Converter<String, BusinessEntity> {

    @Override
    public BusinessEntity convert(String source) {
        if (source.isBlank()) {
            return null;
        }

        return BusinessEntity.valueOf(source.toUpperCase());
    }
}
