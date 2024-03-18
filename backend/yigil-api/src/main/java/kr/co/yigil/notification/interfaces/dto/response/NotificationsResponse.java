package kr.co.yigil.notification.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.notification.interfaces.dto.NotificationInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsResponse {
    private List<NotificationInfoDto> notifications;
    private boolean hasNext;
}