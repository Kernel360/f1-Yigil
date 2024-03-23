package kr.co.yigil.global.utils;

import kr.co.yigil.global.SortBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortByConverter implements Converter<String, SortBy> {
    @Override
    public SortBy convert(String source) {
        return SortBy.valueOf(source.toUpperCase());
    }
}
