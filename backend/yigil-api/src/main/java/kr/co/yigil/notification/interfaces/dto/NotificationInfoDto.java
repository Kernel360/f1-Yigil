package kr.co.yigil.notification.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationInfoDto {

    private String message;
    private String createDate;

}
