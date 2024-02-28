package kr.co.yigil.favor.application;

import kr.co.yigil.favor.domain.FavorInfo;
import kr.co.yigil.favor.domain.FavorService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavorFacade {

    private final FavorService favorService;
    private final NotificationService notificationService;
    public FavorInfo.AddFavorResponse addFavor(final Long memberId, final Long travelId) {
        Long ownerId = favorService.addFavor(memberId, travelId);

        notificationService.sendNotification(NotificationType.FAVOR, memberId, ownerId);
        return new FavorInfo.AddFavorResponse("좋아요가 완료되었습니다.");
    }

    public FavorInfo.DeleteFavorResponse deleteFavor(final Long memberId, final Long travelId) {
        favorService.deleteFavor(memberId, travelId);
        return new FavorInfo.DeleteFavorResponse("좋아요가 취소되었습니다.");
    }
}
