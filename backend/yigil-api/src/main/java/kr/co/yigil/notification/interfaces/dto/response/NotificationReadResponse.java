package kr.co.yigil.notification.interfaces.dto.response;

import lombok.Data;

@Data
public class NotificationReadResponse {
    private String message;

    public NotificationReadResponse(String message) {
        this.message = message;
    }
}
