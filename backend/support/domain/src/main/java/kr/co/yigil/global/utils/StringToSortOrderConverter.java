package kr.co.yigil.global.utils;

import kr.co.yigil.global.SortOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortOrderConverter implements Converter<String, SortOrder> {
    @Override
    public SortOrder convert(String source) {
        return SortOrder.valueOf(source.toUpperCase());
    }
}