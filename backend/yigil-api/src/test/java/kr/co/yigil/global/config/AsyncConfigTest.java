package kr.co.yigil.global.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import kr.co.yigil.global.exception.AsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskExecutor;

public class AsyncConfigTest {

    private final AsyncConfig asyncConfig = new AsyncConfig();

    @DisplayName("TaskExecutor가 잘 구성되었는지")
    @Test
    void taskExecutorConfiguration() {
        TaskExecutor taskExecutor = asyncConfig.taskExecutor();

        assertInstanceOf(ThreadPoolTaskExecutor.class, taskExecutor);
        ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) taskExecutor;

        assertEquals(0, threadPoolTaskExecutor.getCorePoolSize());
        assertEquals("async-task", threadPoolTaskExecutor.getThreadNamePrefix());
    }

    @DisplayName("AsyncUncaughtExceptionHandler가 잘 구성되었는지")
    @Test
    void asyncExceptionHandlerConfiguration() {
        AsyncUncaughtExceptionHandler handler = asyncConfig.getAsyncUncaughtExceptionHandler();

        assertInstanceOf(AsyncExceptionHandler.class, handler);
    }

}