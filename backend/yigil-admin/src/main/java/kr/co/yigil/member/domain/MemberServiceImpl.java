package kr.co.yigil.member.domain;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.MemberStatus;
import kr.co.yigil.member.interfaces.dto.request.MemberBanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberReader memberReader;
    private final MemberStore memberStore;

    @Override
    @Transactional(readOnly = true)
    public Page<Member> getMemberPage(Pageable pageable) {
        return memberReader.getMemberPageRegardlessOfStatus(pageable);
    }

    @Override
    @Transactional
    public void banMembers(MemberBanRequest request) {
        List<Long> memberIds = request.getIds();
        for (Long memberId : memberIds) {
            checkAlreadyBannedMember(memberId);
            memberStore.banMember(memberId);
        }
    }

    private void checkAlreadyBannedMember(Long memberId) {
        if (memberReader.getMemberRegardlessOfStatus(memberId).getStatus()== MemberStatus.BANNED) {
            throw new BadRequestException(ExceptionCode.ALREADY_BANNED);
        }
    }

    @Override
    @Transactional
    public void unbanMembers(MemberBanRequest request) {
        List<Long> memberIds = request.getIds();
for (Long memberId : memberIds) {
            checkAlreadyUnbannedMember(memberId);
            memberStore.unbanMember(memberId);
        }

    }

    private void checkAlreadyUnbannedMember(Long memberId) {
        if (memberReader.getMemberRegardlessOfStatus(memberId).getStatus()== MemberStatus.ACTIVE) {
            throw new BadRequestException(ExceptionCode.ALREADY_UNBANNED);
        }
    }

}
