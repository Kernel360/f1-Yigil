package kr.co.yigil.notification.interfaces.dto.mapper;

import kr.co.yigil.notification.domain.NotificationInfo;
import kr.co.yigil.notification.interfaces.dto.NotificationInfoDto;
import kr.co.yigil.notification.interfaces.dto.response.NotificationsResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface NotificationMapper {

    NotificationsResponse toResponse(NotificationInfo.NotificationsSlice notificationSlice);
    NotificationInfoDto toDto(NotificationInfo.NotificationsUnitInfo notificationInfo);
    
}
