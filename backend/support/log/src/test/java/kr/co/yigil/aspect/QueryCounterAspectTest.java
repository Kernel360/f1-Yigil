package kr.co.yigil.aspect;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.Connection;
import javax.sql.DataSource;
import kr.co.yigil.interceptor.ConnectionProxyHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

public class QueryCounterAspectTest {

    @InjectMocks
    private QueryCounterAspect queryCounterAspect;

    @Mock
    private Connection mockConnection;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("captureConnection이 호출될 때 프록시 객체가 잘 반환되는지")
    @Test
    void testCaptureConnection() throws Throwable {
        when(joinPoint.proceed()).thenReturn(mockConnection);

        Object result = queryCounterAspect.captureConnection(joinPoint);

        assertNotNull(result);
        assertInstanceOf(Connection.class, result);
    }


}
