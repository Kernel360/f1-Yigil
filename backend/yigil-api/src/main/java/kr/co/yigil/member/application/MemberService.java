package kr.co.yigil.member.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.follow.application.FollowRedisIntegrityService;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.repository.FollowCountRepository;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.dto.response.MemberFollowerListResponse;
import kr.co.yigil.member.dto.response.MemberFollowingListResponse;
import kr.co.yigil.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final FollowCountRepository followCountRepository;
    private final FollowRedisIntegrityService followRedisIntegrityService;
    private final ApplicationEventPublisher applicationEventPublisher;


//    public MemberInfoResponse getMemberInfo(final Long memberId) {
//        Member member = findMemberById(memberId);
//        List<Post> postList = postRepository.findAllByMember(member);
//        FollowCount followCount = getMemberFollowCount(member);
//        return MemberInfoResponse.from(member, postList, followCount);
//    }

    private FollowCount getMemberFollowCount(Member member) {
        return followRedisIntegrityService.ensureFollowCounts(member);
    }

//    public MemberUpdateResponse updateMemberInfo(final Long memberId, MemberUpdateRequest request) {
//        Member member = findMemberById(memberId);
//
//        FileUploadEvent event = new FileUploadEvent(this, request.getProfileImageFile(), fileUrl -> {
//            Member updateMember = setMemberInfoUpdated(member, fileUrl, request.getNickname());
//            memberRepository.save(updateMember);
//
//        });
//        applicationEventPublisher.publishEvent(event);
//
//        return new MemberUpdateResponse("회원 정보 업데이트 성공");
//    }

    private Member setMemberInfoUpdated(Member member, String fileUrl, String nickname) {
        return new Member(member.getId(), member.getEmail(), member.getSocialLoginId(),
                nickname, fileUrl, member.getSocialLoginType());
    }

    public MemberDeleteResponse withdraw(final Long memberId) {
        Member member = findMemberById(memberId);
        memberRepository.delete(member);
        return new MemberDeleteResponse("회원 탈퇴 성공");
    }

    public MemberFollowerListResponse getFollowerList(final Long memberId) {
        Member member = findMemberById(memberId);
        List<Member> followers = followRepository.findAllByFollowing(member)
                .stream().map(Follow::getFollower).collect(Collectors.toList());
        return new MemberFollowerListResponse(followers);
    }

    public MemberFollowingListResponse getFollowingList(final Long memberId) {
        Member member = findMemberById(memberId);
        List<Member> followings = followRepository.findAllByFollower(member)
                .stream().map(Follow::getFollowing).collect(Collectors.toList());
        return new MemberFollowingListResponse(followings);
    }

    public Member findMemberById(Long memberId){
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }
}