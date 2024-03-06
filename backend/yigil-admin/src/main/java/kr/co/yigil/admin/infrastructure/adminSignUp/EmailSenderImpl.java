package kr.co.yigil.admin.infrastructure.adminSignUp;

import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.admin.domain.adminSignUp.EmailSender;
import kr.co.yigil.email.EmailEventType;
import kr.co.yigil.email.EmailSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendAcceptEmail(AdminSignUp signUp, String password) {
        EmailSendEvent event = new EmailSendEvent(this, signUp.getEmail(),
                "[이길로그] 관리자 서비스 가입이 완료되었습니다.", "", password,
                EmailEventType.ADMIN_SIGN_UP_ACCEPT);
        eventPublisher.publishEvent(event);
    }

    @Override
    public void sendRejectEmail(AdminSignUp signUp) {
        EmailSendEvent event = new EmailSendEvent(this, signUp.getEmail(),
                "[이길로그] 관리자 서비스 가입이 거절되었습니다.", "", "", EmailEventType.ADMIN_SIGN_UP_REJECT);
        eventPublisher.publishEvent(event);
    }

}
