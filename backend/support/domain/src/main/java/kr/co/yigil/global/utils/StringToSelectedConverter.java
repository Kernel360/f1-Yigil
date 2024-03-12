package kr.co.yigil.global.utils;

import kr.co.yigil.global.Selected;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSelectedConverter implements Converter<String, Selected> {
    @Override
    public Selected convert(@NotNull String source) {
        return Selected.valueOf(source.toUpperCase());
    }
}
