package com.ubs.sis.admin.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MiscUtils {

    public static <E> List<E> getEmptyIfNull(List<E> arr) {
        return Optional.ofNullable(arr).orElse(new ArrayList<>());
    }

    public static Date getDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
