package kr.co.yigil.notification.interfaces.dto.request;


import lombok.Data;

import java.util.List;

@Data
public class NotificationReadRequest {
    private List<Long> ids;
}
