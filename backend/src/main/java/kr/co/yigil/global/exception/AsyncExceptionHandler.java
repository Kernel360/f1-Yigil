package kr.co.yigil.global.exception;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {


    @Override
    public void handleUncaughtException(@NotNull Throwable ex, @NotNull Method method, @NotNull Object... params) {
        log.error(ex.toString(), ex.getMessage(), ex);
    }
}