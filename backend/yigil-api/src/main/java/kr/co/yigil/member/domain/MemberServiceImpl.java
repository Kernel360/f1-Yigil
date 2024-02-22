package kr.co.yigil.member.domain;

import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberCommand.RegisterMemberRequest;
import kr.co.yigil.member.interfaces.dto.request.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService{
    private final MemberReader memberReader;
    private final MemberStore memberStore;

    @Override
    public Member registerMember(RegisterMemberRequest request) {
        //todo member 생성 로직 추가
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Member retrieveMember(Long memberId) {
        return memberReader.getMember(memberId);
    }

    @Override
    @Transactional
    public void withdrawal(Long memberId) {
        memberStore.withdrawal(memberId);
    }

    @Override
    @Transactional
    public Member updateMemberInfo(Long memberId, MemberUpdateRequest request) {
        var member = memberReader.getMember(memberId);

        var fileUrl = FileUploadUtil.predictAttachFile(request.getProfileImageFile());

        Member updateMember = setMemberInfoUpdated(member, fileUrl.getFileUrl(), request.getNickname()
            , Ages.from(request.getAges())
            , Gender.from(request.getGender()));
        return memberStore.save(updateMember);
    }

    private Member setMemberInfoUpdated(Member member, String fileUrl, String nickname, Ages ages, Gender gender) {
        return new Member(member.getId(), member.getEmail(), member.getSocialLoginId(),
            nickname, fileUrl, member.getSocialLoginType(), ages, gender);
    }
}
