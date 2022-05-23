package com.ubs.sis.admin.config.converter;


import org.springframework.core.convert.converter.Converter;

public final class LongConverter implements Converter<String, Long> {
    @Override
    public Long convert(String s) {
        return Long.parseLong(s);
    }
}
