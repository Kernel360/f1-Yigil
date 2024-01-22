package kr.co.yigil.filter;

import static kr.co.yigil.MdcPreference.REQUEST_ID;
import static kr.co.yigil.MdcPreference.REQUEST_IP;
import static kr.co.yigil.MdcPreference.REQUEST_METHOD;
import static kr.co.yigil.MdcPreference.REQUEST_TIME;
import static kr.co.yigil.MdcPreference.REQUEST_URI;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class MdcLoggingFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final
    FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        setMdc(httpRequest);
        chain.doFilter(request, response);
        MDC.clear();
    }

    private void setMdc(final HttpServletRequest request) {
        MDC.put(REQUEST_ID.name(), UUID.randomUUID().toString());
        MDC.put(REQUEST_METHOD.name(), request.getMethod());
        MDC.put(REQUEST_URI.name(), request.getRequestURI());
        MDC.put(REQUEST_TIME.name(), LocalDateTime.now().toString());
        MDC.put(REQUEST_IP.name(), request.getRemoteAddr());
    }
}
