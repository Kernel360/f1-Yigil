package kr.co.yigil.notification.interfaces.dto.mapper;

import java.util.List;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.interfaces.dto.NotificationInfoDto;
import kr.co.yigil.notification.interfaces.dto.response.NotificationsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    default NotificationsResponse notificationSliceToNotificationsResponse(Slice<Notification> notificationSlice) {
        List<NotificationInfoDto> notificationInfoDtoList = notificationsToNotificationInfoDtoList(notificationSlice.getContent());
        boolean hasNext = notificationSlice.hasNext();
        return new NotificationsResponse(notificationInfoDtoList, hasNext);
    }

    default List<NotificationInfoDto> notificationsToNotificationInfoDtoList(List<Notification> notifications) {
        return notifications.stream()
                .map(this::notificationToNotificationInfoDto)
                .toList();
    }

    @Mapping(target = "notificationId", source = "id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "createDate", expression = "java(notification.getCreatedAt().toString())")
    @Mapping(target = "isRead", source = "read")
    NotificationInfoDto notificationToNotificationInfoDto(Notification notification);
}
