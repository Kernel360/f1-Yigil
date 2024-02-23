package kr.co.yigil.member.domain;

import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberCommand.RegisterMemberRequest;
import kr.co.yigil.member.domain.MemberInfo.MemberCourseResponse;
import kr.co.yigil.member.domain.MemberInfo.MemberSpotResponse;
import kr.co.yigil.travel.domain.course.CourseReader;
import kr.co.yigil.travel.domain.spot.SpotReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final CourseReader courseReader;
    private final SpotReader spotReader;

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
    public Member updateMemberInfo(Long memberId, MemberCommand.MemberUpdateRequest request) {
        var member = memberReader.getMember(memberId);

        var fileUrl = FileUploadUtil.predictAttachFile(request.getProfileImageFile());

        Member updateMember = setMemberInfoUpdated(member, fileUrl.getFileUrl(), request.getNickname()
            , Ages.from(request.getAges())
            , Gender.from(request.getGender()));
        return memberStore.save(updateMember);
    }


    @Override
    public MemberSpotResponse retrieveSpotList(Long memberId, Pageable pageable, String selectInfo) {
        return spotReader.findAllByMemberId(memberId, pageable, selectInfo);
    }

    @Override
    public MemberCourseResponse retrieveCourseList(Long memberId, Pageable pageable, String selectInfo) {
        return courseReader.findAllByMemberId(memberId, pageable, selectInfo);
    }

    private Member setMemberInfoUpdated(Member member, String fileUrl, String nickname, Ages ages, Gender gender) {
        return new Member(member.getId(), member.getEmail(), member.getSocialLoginId(),
            nickname, fileUrl, member.getSocialLoginType(), ages, gender);
    }
}
