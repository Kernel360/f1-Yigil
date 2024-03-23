package kr.co.yigil.travel.spot.application;


import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotDetailInfo;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotPageInfo;
import kr.co.yigil.travel.spot.domain.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotFacade {

    private final SpotService spotService;
    private final NotificationService notificationService;
    private final AdminService adminService;

    public SpotPageInfo getSpots(Pageable pageable) {
        return spotService.getSpots(pageable);
    }

    public SpotDetailInfo getSpot(Long spotId) {
        return spotService.getSpot(spotId);
    }

    public void deleteSpot(Long spotId) {
        var memberId = spotService.deleteSpot(spotId);
        var adminId = adminService.getAdminId();
        notificationService.saveNotification(NotificationType.SPOT_DELETED, adminId, memberId);
    }

}
