package kr.co.yigil.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.MailException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailSendEventListener {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    @EventListener
    public void handleAcceptEmailSend(EmailSendEvent event) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(event.getTo());
            mimeMessageHelper.setSubject(event.getSubject());
            mimeMessageHelper.setText(setContext(event), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailException(ExceptionCode.SMTP_SERVER_ERROR);
        }
    }

    private String setContext(EmailSendEvent event) {
        return switch (event.getType()) {
            case ADMIN_SIGN_UP_ACCEPT -> setAdminSignupContext(event);
            case ADMIN_SIGN_UP_REJECT -> setAdminRejectContext(event);
            case REPORT_REPLY -> setReportReplyContext(event);
            default -> throw new BadRequestException(ExceptionCode.INVALID_EMAIL_TYPE);
        };
    }

    private String setReportReplyContext(EmailSendEvent event) {
        Context context = new Context();
        context.setVariable("result", event.getMessage());
        context.setVariable("reportContent", event.getKey());
        return templateEngine.process(event.getType().toString(), context);
    }

    private String setAdminRejectContext(EmailSendEvent event) {
        Context context = new Context();
        return templateEngine.process(event.getType().toString(), context);
    }

    private String setAdminSignupContext(EmailSendEvent event) {
        Context context = new Context();
        context.setVariable("password", event.getKey());
        return templateEngine.process(event.getType().toString(), context);
    }
}
