package kr.co.yigil.global.log.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(@NotNull HttpServletRequest request, String message) {
        if(!message.contains("prometheus")) {
            logger.info(message);
        }
    }

    @Override
    protected void afterRequest(@NotNull HttpServletRequest request, @NotNull String message) {

    }
}
