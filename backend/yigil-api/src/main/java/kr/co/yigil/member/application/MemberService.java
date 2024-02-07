package kr.co.yigil.member.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

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
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import kr.co.yigil.member.repository.MemberRepository;
import kr.co.yigil.travel.Course;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.response.CourseFindDto;
import kr.co.yigil.travel.dto.response.SpotFindDto;
import kr.co.yigil.travel.repository.CourseRepository;
import kr.co.yigil.travel.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final CourseRepository courseRepository;
    private final SpotRepository spotRepository;
    private final FollowRedisIntegrityService followRedisIntegrityService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;

    public MemberInfoResponse getMemberInfo(final Long memberId) {
        Member member = findMemberById(memberId);
        FollowCount followCount = getMemberFollowCount(member);
        return MemberInfoResponse.from(member, followCount);
    }

    public Slice<CourseFindDto> getMemberCourseInfo(final Long memberId, Pageable pageable) {
        Member member = findMemberById(memberId);
        Slice<Course> courseList = courseRepository.findAllByMember(member);
        List<CourseFindDto> courseFindDtoList = courseList.stream()
            .map(this::getCourseFindDto)
            .toList();
        return new SliceImpl<>(courseFindDtoList, pageable, courseList.hasNext());
    }

    public Slice<SpotFindDto> getMemberSpotInfo(final Long memberId, Pageable pageable) {
        Slice<Spot> spotList = spotRepository.findAllByMemberAndIsInCourseFalse(memberId, pageable);
        List<SpotFindDto> spotFindDtoList = spotList.stream()
            .map(this::getSpotFindDto)
            .toList();
        return new SliceImpl<>(spotFindDtoList, pageable, spotList.hasNext());
    }

    private FollowCount getMemberFollowCount(Member member) {
        return followRedisIntegrityService.ensureFollowCounts(member);
    }

    public MemberUpdateResponse updateMemberInfo(final Long memberId, MemberUpdateRequest request) {
        Member member = findMemberById(memberId);
        FileUploadEvent event = new FileUploadEvent(this, request.getProfileImageFile(), fileUrl -> {
            Member updateMember = setMemberInfoUpdated(member, String.valueOf(fileUrl), request.getNickname()
                ,Ages.from(request.getAges())
                ,Gender.from(request.getGender()));
            memberRepository.save(updateMember);
        });
        applicationEventPublisher.publishEvent(event);

        return new MemberUpdateResponse("회원 정보 업데이트 성공");
    }

    private Member setMemberInfoUpdated(Member member, String fileUrl, String nickname, Ages ages, Gender gender) {
        return new Member(member.getId(), member.getEmail(), member.getSocialLoginId(),
                nickname, fileUrl, member.getSocialLoginType(), ages, gender);
    }

    public MemberDeleteResponse withdraw(final Long memberId) {
        Member member = findMemberById(memberId);
        memberRepository.delete(member);
        return new MemberDeleteResponse("회원 탈퇴 성공");
    }

    public Slice<FollowerFindDto> getFollowerList(final Long memberId, Pageable pageable) {
        Member member = findMemberById(memberId);
        Slice<Follow> followerList = followRepository.findAllByFollower(member);
        List<FollowerFindDto> followers = followerList.stream()
            .map(this::getFollowerFindDto)
            .toList();
        return new SliceImpl<>(followers, pageable, followerList.hasNext());
    }

    public Slice<FollowingFindDto> getFollowingList(final Long memberId, Pageable pageable) {
        Member member = findMemberById(memberId);
        Slice<Follow> followingList = followRepository.findAllByFollowing(member);
        List<FollowingFindDto> followings = followingList.stream()
            .map(this::getFollowingFindDto)
            .toList();
        return new SliceImpl<>(followings, pageable, followingList.hasNext()) {
        };
    }

    public Member findMemberById(Long memberId){
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }

    @NotNull
    public SpotFindDto getSpotFindDto(Spot spot) {
        return SpotFindDto.from(
            spot,
            favorRedisIntegrityService.ensureFavorCounts(spot).getFavorCount(),
            commentRedisIntegrityService.ensureCommentCount(spot).getCommentCount());
    }

    @NotNull
    public CourseFindDto getCourseFindDto(Course course) {
        return CourseFindDto.from(
            course,
            favorRedisIntegrityService.ensureFavorCounts(course).getFavorCount(),
            commentRedisIntegrityService.ensureCommentCount(course).getCommentCount());
    }
    @NotNull
    public FollowerFindDto getFollowerFindDto(Follow follow) {
        return FollowerFindDto.from(
            follow
        );
    }

    @NotNull
    public FollowingFindDto getFollowingFindDto(Follow follow) {
        return FollowingFindDto.from(
            follow
        );
    }
}