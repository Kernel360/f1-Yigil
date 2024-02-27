package kr.co.yigil.member.domain;

import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.member.domain.MemberInfo.FollowerResponse;
import kr.co.yigil.member.domain.MemberInfo.FollowingResponse;
import kr.co.yigil.member.domain.MemberInfo.Main;
import kr.co.yigil.travel.domain.TravelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final TravelReader travelReader;
    private final FollowReader followReader;

    @Override
    @Transactional
    public Main retrieveMemberInfo(Long memberId) {
        var member = memberReader.getMember(memberId);
        var followCount = followReader.getFollowCount(memberId);
        return new Main(member, followCount);
    }

    @Override
    @Transactional
    public void withdrawal(Long memberId) {
        memberStore.deleteMember(memberId);
    }

    @Override
    @Transactional
    public boolean updateMemberInfo(Long memberId, MemberCommand.MemberUpdateRequest request) {

        var member = memberReader.getMember(memberId);
        var currentProfileUrl = member.getProfileImageUrl();
        var updatedProfile = FileUploadUtil.predictAttachFile(request.getProfileImageFile());

        member.updateMemberInfo(request.getNickname(), request.getAges(), request.getGender(),
            updatedProfile.getFileUrl());

        return currentProfileUrl == null || !currentProfileUrl.equals(updatedProfile.getFileUrl());
    }

    @Override
    @Transactional
    public FollowerResponse getFollowerList(Long memberId, Pageable pageable) {
        memberReader.validateMember(memberId);
        var followerSlice = followReader.getFollowerSlice(memberId, pageable);
        var followerList = followerSlice.getContent().stream()
            .map(Follow::getFollower)
            .map(MemberInfo.FollowInfo::new)
            .toList();
        return new MemberInfo.FollowerResponse(followerList, followerSlice.hasNext());
    }

    @Override
    @Transactional
    public FollowingResponse getFollowingList(Long memberId, Pageable pageable) {
        memberReader.validateMember(memberId);
        var followingSlice = followReader.getFollowingSlice(memberId, pageable);
        var followingList = followingSlice.getContent().stream()
            .map(Follow::getFollowing)
            .map(MemberInfo.FollowInfo::new)
            .toList();
        return new FollowingResponse(followingList, followingSlice.hasNext());
    }
}
