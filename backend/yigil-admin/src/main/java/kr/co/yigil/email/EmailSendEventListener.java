package kr.co.yigil.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.MailException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
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
        Context context = new Context();
        if(event.getType() == EmailEventType.ADMIN_SIGN_UP_ACCEPT)
            context.setVariable("password", event.getKey());

        return templateEngine.process(event.getType().toString(), context);
    }


}
