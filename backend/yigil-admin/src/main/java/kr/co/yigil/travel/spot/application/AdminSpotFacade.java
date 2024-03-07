package kr.co.yigil.travel.spot.application;


import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto.AdminSpotDetailInfo;
import kr.co.yigil.travel.spot.domain.AdminSpotInfoDto.AdminSpotList;
import kr.co.yigil.travel.spot.domain.AdminSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSpotFacade {

    private final AdminSpotService adminSpotService;
    private final NotificationService notificationService;
    private final AdminService adminService;

    public AdminSpotList getSpots(Pageable pageable) {
        return adminSpotService.getSpots(pageable);
    }

    public AdminSpotDetailInfo getSpot(Long spotId) {
        return adminSpotService.getSpot(spotId);
    }

    public void deleteSpot(Long spotId) {

        var memberId = adminSpotService.deleteSpot(spotId);

        var adminId = getAdminId();

        notificationService.sendNotification(NotificationType.SPOT_DELETED, adminId, memberId);
    }

    private Long getAdminId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            String userEmail = userDetails.getUsername();
            Admin admin = adminService.getAdmin(userEmail);
            return admin.getId();
        }

        throw new BadRequestException(ExceptionCode.ADMIN_NOT_FOUND);

    }
}
