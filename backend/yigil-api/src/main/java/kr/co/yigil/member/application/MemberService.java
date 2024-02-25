package kr.co.yigil.member.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import java.util.List;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowCacheReader;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
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
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.infrastructure.CourseRepository;
import kr.co.yigil.travel.infrastructure.SpotRepository;
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
    private final CourseRepository courseRepository;
    private final SpotRepository spotRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final FollowCacheReader followCacheReader;
    private final FollowReader followReader;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;

    public MemberInfoResponse getMemberInfo(final Long memberId) {
        Member member = findMemberById(memberId);
        FollowCount followCount = getMemberFollowCount(member);
        return MemberInfoResponse.from(member, followCount);
    }



    private FollowCount getMemberFollowCount(Member member) {
        return followCacheReader.getFollowCount(member.getId());
    }

    public MemberUpdateResponse updateMemberInfo(final Long memberId, MemberUpdateRequest request) {
        Member member = findMemberById(memberId);
        FileUploadEvent event = new FileUploadEvent(this, request.getProfileImageFile());
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
        Slice<Follow> followerList = followReader.getFollowerSlice(memberId, pageable);
        List<FollowerFindDto> followers = followerList.stream()
            .map(this::getFollowerFindDto)
            .toList();
        return new SliceImpl<>(followers, pageable, followerList.hasNext());
    }

    public Slice<FollowingFindDto> getFollowingList(final Long memberId, Pageable pageable) {
        Member member = findMemberById(memberId);
        Slice<Follow> followingList = followReader.getFollowingSlice(memberId, pageable);
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

//    @NotNull
//    public SpotFindDto getSpotFindDto(Spot spot) {
//        return SpotFindDto.from(
//            spot,
//            favorRedisIntegrityService.ensureFavorCounts(spot).getFavorCount(),
//            commentRedisIntegrityService.ensureCommentCount(spot).getCommentCount());
//    }
//
//    @NotNull
//    public CourseFindDto getCourseFindDto(Course course) {
//        return CourseFindDto.from(
//            course,
//            favorRedisIntegrityService.ensureFavorCounts(course).getFavorCount(),
//            commentRedisIntegrityService.ensureCommentCount(course).getCommentCount());
//    }
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