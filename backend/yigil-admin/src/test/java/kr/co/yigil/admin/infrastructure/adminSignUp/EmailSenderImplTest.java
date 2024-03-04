package kr.co.yigil.admin.infrastructure.adminSignUp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import kr.co.yigil.email.EmailEventType;
import kr.co.yigil.email.EmailSendEvent;
import kr.co.yigil.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;


@ExtendWith(MockitoExtension.class)
class EmailSenderImplTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private EmailSenderImpl emailSender;

    @Captor
    private ArgumentCaptor<EmailSendEvent> eventCaptor;

    @BeforeEach
    void setUp() {
        // Initialize your setup here
    }

    @DisplayName("sendAcceptEmail 메서드가 이메일 이벤트를 잘 발행하는지")
    @Test
    void sendAcceptEmail_ShouldPublishEvent() {
        AdminSignUp signUp = mock(AdminSignUp.class);
        String password = "password";

        emailSender.sendAcceptEmail(signUp, password);

        verify(eventPublisher).publishEvent(eventCaptor.capture());

    }

    @DisplayName("sendRejectEmail 메서드가 이메일 이벤트를 잘 발행하는지")
    @Test
    void sendRejectEmail_ShouldPublishEvent() {
        AdminSignUp signUp = mock(AdminSignUp.class);

        emailSender.sendRejectEmail(signUp);

        verify(eventPublisher).publishEvent(eventCaptor.capture());

    }
}