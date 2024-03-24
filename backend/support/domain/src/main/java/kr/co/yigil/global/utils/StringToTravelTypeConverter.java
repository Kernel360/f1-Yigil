package kr.co.yigil.global.utils;

import kr.co.yigil.travel.TravelType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class StringToTravelTypeConverter implements Converter<String, TravelType>{
    @Override
    public TravelType convert(String source) {
        return TravelType.valueOf(source.toUpperCase());
    }
}
