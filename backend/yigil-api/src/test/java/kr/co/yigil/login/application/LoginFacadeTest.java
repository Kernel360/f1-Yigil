package kr.co.yigil.login.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.domain.LoginStrategyManager;
import kr.co.yigil.login.infrastructure.LoginStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoginFacadeTest {
    @Mock
    private LoginStrategyManager loginStrategyManager;

    @InjectMocks
    private LoginFacade loginFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("executeLoginStrategy 메서드가 LoginStrategyManager의 메서드를 잘 호출하는지")
    @Test
    void executeLoginStrategy() {
        LoginCommand.LoginRequest loginCommand = mock(LoginCommand.LoginRequest.class);
        when(loginCommand.getProvider()).thenReturn("provider");
        LoginStrategy loginStrategy = mock(LoginStrategy.class);
        when(loginStrategyManager.getLoginStrategy(loginCommand.getProvider())).thenReturn(loginStrategy);
        when(loginStrategy.processLogin(loginCommand, "accessToken")).thenReturn(1L);

        loginFacade.executeLoginStrategy(loginCommand, "accessToken");

        verify(loginStrategyManager, times(1)).getLoginStrategy(loginCommand.getProvider());
        verify(loginStrategy, times(1)).processLogin(loginCommand, "accessToken");
        assertThat(loginFacade.executeLoginStrategy(loginCommand, "accessToken")).isEqualTo(1L);
    }


}