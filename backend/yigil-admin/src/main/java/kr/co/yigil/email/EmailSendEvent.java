package kr.co.yigil.email;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailSendEvent extends ApplicationEvent {
    private final String to;
    private final String subject;
    private final String message;
    private final String key;
    private final EmailEventType type;

    public EmailSendEvent(Object source, String email, String subject, String message, String key, EmailEventType type) {
        super(source);
        to = email;
        this.subject = subject;
        this.message = message;
        this.key = key;
        this.type = type;
    }
}
