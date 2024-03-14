package kr.co.yigil.member.domain;

import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberCommand.MemberUpdateRequest;
import kr.co.yigil.member.domain.MemberInfo.Main;
import kr.co.yigil.region.domain.MemberRegion;
import kr.co.yigil.region.domain.RegionReader;
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
    private final RegionReader regionReader;

    @Override
    @Transactional
    public Main retrieveMemberInfo(final Long memberId) {
        var member = memberReader.getMember(memberId);
        var followCount = followReader.getFollowCount(memberId);
        return new Main(member, followCount);
    }

    @Override
    @Transactional
    public void withdrawal(final Long memberId) {
        memberStore.deleteMember(memberId);
    }

    @Override
    @Transactional
    public void updateMemberInfo(final Long memberId,
        final MemberCommand.MemberUpdateRequest request) {

        var member = memberReader.getMember(memberId);

        AttachFile updatedProfile = getAttachFile(request);

        var memberRegions = getMemberRegions(request, member);

        member.updateMemberInfo(request.getNickname(), request.getAges(), request.getGender(),
            updatedProfile, memberRegions);

    }

    private List<MemberRegion> getMemberRegions(MemberUpdateRequest request, Member member) {
        if (request.getFavoriteRegionIds() == null) {
            return null;
        }

        return regionReader.getRegions(request.getFavoriteRegionIds())
            .stream().map(region -> new MemberRegion(member, region))
            .toList();
    }

    @Override
    public MemberInfo.NicknameCheckInfo nicknameDuplicateCheck(String nickname) {

        if (memberReader.existsByNickname(nickname.trim())) {
            return new MemberInfo.NicknameCheckInfo(false);
        }
        return new MemberInfo.NicknameCheckInfo(true);
    }

    private AttachFile getAttachFile(final MemberUpdateRequest request) {
        if (request.getProfileImageFile() == null && request.getIsProfileEmpty() == null) {
            return null;
        }
        if (request.getProfileImageFile() == null &&  Boolean.FALSE.equals(request.getIsProfileEmpty())) {
            return null;
        }

        if (request.getProfileImageFile() == null && Boolean.TRUE.equals(request.getIsProfileEmpty())) {
            return AttachFile.of("");
        }

        if (request.getProfileImageFile() != null && Boolean.TRUE.equals(request.getIsProfileEmpty())) {
            throw new BadRequestException(ExceptionCode.INVALID_REQUEST);
        }

        return fileUploader.upload(request.getProfileImageFile());
    }
}
