package kr.co.yigil.member.application;

import java.util.List;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.follow.application.FollowRedisIntegrityService;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.response.FollowerFindDto;
import kr.co.yigil.follow.dto.response.FollowingFindDto;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberService;
import kr.co.yigil.member.interfaces.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.interfaces.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.interfaces.dto.response.MemberInfoResponse;
import kr.co.yigil.member.interfaces.dto.response.MemberUpdateResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotService;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import kr.co.yigil.travel.interfaces.dto.response.CourseFindDto;
import kr.co.yigil.travel.interfaces.dto.response.SpotFindDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    public MemberInfoResponse getMemberInfo(final Long memberId) {

        Member member = memberService.retrieveMember(memberId);
        // todo 석희님 캐싱 처리 부분 참고 follow 도메인 변경 시 변경
        FollowCount followCount = getMemberFollowCount(member);
        return MemberInfoResponse.from(member, followCount);
    }

    public Slice<CourseFindDto> getMemberCourseInfo(final Long memberId, Pageable pageable) {
        Member member = memberService.retrieveMember(memberId);
        // todo course도메인 리팩토링 후 service 참조
        Slice<Course> courseList = courseRepository.findAllByMember(member);
        List<CourseFindDto> courseFindDtoList = courseList.stream()
            .map(this::getCourseFindDto)
            .toList();
        return new SliceImpl<>(courseFindDtoList, pageable, courseList.hasNext());
    }

    public Slice<SpotFindDto> getMemberSpotInfo(final Long memberId, Pageable pageable) {

        Page<Spot> spotList = spotService.getSpotListByMemberId(memberId, pageable);
        List<SpotFindDto> spotFindDtoList = spotList.stream()
            .map(this::getSpotFindDto)
            .toList();
        return new SliceImpl<>(spotFindDtoList, pageable, spotList.hasNext());
    }

    private FollowCount getMemberFollowCount(Member member) {
        return followRedisIntegrityService.ensureFollowCounts(member);
    }

    public MemberUpdateResponse updateMemberInfo(final Long memberId, MemberUpdateRequest request) {
        var updatedMember = memberService.updateMemberInfo(memberId, request);
        FileUploadEvent event = new FileUploadEvent(this, request.getProfileImageFile());
        applicationEventPublisher.publishEvent(event);

        return new MemberUpdateResponse("회원 정보 업데이트 성공");
    }

    public MemberDeleteResponse withdraw(final Long memberId) {
        memberService.withdrawal(memberId);
        return new MemberDeleteResponse("회원 탈퇴 성공");
    }

    public Slice<FollowerFindDto> getFollowerList(final Long memberId, Pageable pageable) {
        Member member = memberService.retrieveMember(memberId);
        Slice<Follow> followerList = followRepository.findAllByFollower(member);
        List<FollowerFindDto> followers = followerList.stream()
            .map(this::getFollowerFindDto)
            .toList();
        return new SliceImpl<>(followers, pageable, followerList.hasNext());
    }

    public Slice<FollowingFindDto> getFollowingList(final Long memberId, Pageable pageable) {
        Member member = memberService.retrieveMember(memberId);
        Slice<Follow> followingList = followRepository.findAllByFollowing(member);
        List<FollowingFindDto> followings = followingList.stream()
            .map(this::getFollowingFindDto)
            .toList();
        return new SliceImpl<>(followings, pageable, followingList.hasNext()) {
        };
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
    @NotNull
    public FollowerFindDto getFollowerFindDto(Follow follow) {
        return FollowerFindDto.from(follow);
    }

    @NotNull
    public FollowingFindDto getFollowingFindDto(Follow follow) {
        return FollowingFindDto.from(follow);
    }
}