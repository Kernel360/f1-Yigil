package kr.co.yigil.member.application;

import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.member.domain.MemberCommand;
import kr.co.yigil.member.domain.MemberCommand.CoursesVisibilityRequest;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.domain.MemberInfo.FollowingResponse;
import kr.co.yigil.member.domain.MemberService;
import kr.co.yigil.member.interfaces.dto.response.MemberDeleteResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotService;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;
    private final SpotService spotService;
    private final FollowRepository followRepository;
    private final CourseRepository courseRepository;
    private final FollowRedisIntegrityService followRedisIntegrityService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;

    public MemberInfo.Main getMemberInfo(final Long memberId) {
        return memberService.retrieveMemberInfo(memberId);
    }

    public MemberInfo.MemberCourseResponse getMemberCourseInfo(final Long memberId,
        Pageable pageable, String selected) {
        return memberService.retrieveCourseList(memberId, pageable, selected);
    }

    public MemberInfo.MemberSpotResponse getMemberSpotInfo(final Long memberId, Pageable pageable,
        String selected) {
        return memberService.retrieveSpotList(memberId, pageable, selected);
    }

    public MemberInfo.MemberUpdateResponse updateMemberInfo(final Long memberId,
        MemberCommand.MemberUpdateRequest request) {

        memberService.updateMemberInfo(memberId, request);
        FileUploadEvent event = new FileUploadEvent(this, request.getProfileImageFile());
        applicationEventPublisher.publishEvent(event);
        return new MemberInfo.MemberUpdateResponse("회원 정보 업데이트 성공");
    }

    public MemberDeleteResponse withdraw(final Long memberId) {
        memberService.withdrawal(memberId);
        return new MemberDeleteResponse("회원 탈퇴 성공");
    }

    public MemberInfo.FollowerResponse getFollowerList(final Long memberId, Pageable pageable) {
        return memberService.getFollowerList(memberId, pageable);
    }

    public FollowingResponse getFollowingList(final Long memberId, Pageable pageable) {
        return memberService.getFollowingList(memberId, pageable);
    }

    public MemberInfo.CoursesVisibilityResponse setCoursesVisibility(Long memberId,
        CoursesVisibilityRequest memberCommand) {
        return memberService.setCoursesVisibility(memberId, memberCommand);
    }

    @NotNull
    public SpotFindDto getSpotFindDto(Spot spot) {
        int favorCount = favorRedisIntegrityService.ensureFavorCounts(spot).getFavorCount();
        int commentCount = commentRedisIntegrityService.ensureCommentCount(spot).getCommentCount();
        return SpotFindDto.from(spot, favorCount, commentCount);
    }

    @NotNull
    public CourseFindDto getCourseFindDto(Course course) {
        int favorCount = favorRedisIntegrityService.ensureFavorCounts(course).getFavorCount();
        int commentCount = commentRedisIntegrityService.ensureCommentCount(course)
            .getCommentCount();
        return CourseFindDto.from(course, favorCount, commentCount);
    }
}