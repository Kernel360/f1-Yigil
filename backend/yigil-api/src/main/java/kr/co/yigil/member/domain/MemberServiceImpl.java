package kr.co.yigil.member.domain;

import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.member.domain.MemberInfo.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final FollowReader followReader;
    private final FileUploader fileUploader;

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
    public void updateMemberInfo(Long memberId, MemberCommand.MemberUpdateRequest request) {

        var member = memberReader.getMember(memberId);
        var updatedProfile = FileUploadUtil.predictAttachFile(request.getProfileImageFile());

        member.updateMemberInfo(request.getNickname(), request.getAges(), request.getGender(),
            updatedProfile.getFileUrl());

        fileUploader.upload(request.getProfileImageFile(), updatedProfile.getFileName());
    }
}
