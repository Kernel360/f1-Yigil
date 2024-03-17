package kr.co.yigil.email;

import lombok.Getter;

@Getter
public class EmailSendEvent  {
    private final String to;
    private final String subject;
    private final String message;
    private final String key;
    private final EmailEventType type;

    public EmailSendEvent( String email, String subject, String message, String key, EmailEventType type) {

        to = email;
        this.subject = subject;
        this.message = message;
        this.key = key;
        this.type = type;
    }
}
