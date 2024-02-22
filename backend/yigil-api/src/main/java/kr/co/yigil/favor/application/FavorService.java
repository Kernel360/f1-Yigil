package kr.co.yigil.favor.application;

import jakarta.transaction.Transactional;
import kr.co.yigil.favor.domain.Favor;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.favor.domain.repository.FavorCountRepository;
import kr.co.yigil.favor.domain.repository.FavorRepository;
import kr.co.yigil.favor.dto.response.AddFavorResponse;
import kr.co.yigil.favor.dto.response.DeleteFavorResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.travel.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavorService {

    private final MemberRepository memberRepository;
    private final FavorRepository favorRepository;
    private final FavorCountRepository favorCountRepository;
    private final NotificationService notificationService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;
    private final TravelService travelService;

    @Transactional
    public AddFavorResponse addFavor(final Long memberId, final Long travelId) {
        Member member = getMemberById(memberId);
        Travel travel = travelService.findTravelById(travelId);
        favorRepository.save(new Favor(member, travel));
        incrementFavorCount(travel);
        sendFavorNotification(travel, member);
        return new AddFavorResponse("좋아요가 완료되었습니다.");
    }

    @Transactional
    public DeleteFavorResponse deleteFavor(final Long memberId, final Long travelId) {
        Member member = getMemberById(memberId);
        Travel travel = travelService.findTravelById(travelId);
        favorRepository.deleteByMemberAndTravel(member, travel);
        decrementFavorCount(travel);
        return new DeleteFavorResponse("좋아요가 취소되었습니다.");
    }

    private void sendFavorNotification(Travel travel, Member member) {
        String message = member.getNickname() + "님이 게시글에 좋아요를 눌렀습니다.";
        Notification notify = new Notification(travel.getMember(), message);
        notificationService.sendNotification(notify);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
    }

    private void incrementFavorCount(Travel travel) {
        FavorCount favorCount = favorRedisIntegrityService.ensureFavorCounts(travel);
        favorCount.incrementFavorCount();
        favorCountRepository.save(favorCount);
    }

    private void decrementFavorCount(Travel travel) {
        FavorCount favorCount = favorRedisIntegrityService.ensureFavorCounts(travel);
        favorCount.decrementFavorCount();
        favorCountRepository.save(favorCount);
    }
}
