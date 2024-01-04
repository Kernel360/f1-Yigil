package kr.co.yigil.global.log.filter;

import static kr.co.yigil.global.log.MdcPref.REQUEST_ID;
import static kr.co.yigil.global.log.MdcPref.REQUEST_IP;
import static kr.co.yigil.global.log.MdcPref.REQUEST_METHOD;
import static kr.co.yigil.global.log.MdcPref.REQUEST_TIME;
import static kr.co.yigil.global.log.MdcPref.REQUEST_URI;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.slf4j.MDC;
import java.util.UUID;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(HIGHEST_PRECEDENCE)
@Slf4j
public class MdcLoggingFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final
            FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        setMdc(httpRequest);
        chain.doFilter(request, response);
//        log.info("Received request: Method: {}, URI: {}, IP: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getRemoteAddr());
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
