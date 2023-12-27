package kr.co.yigil.member.domain.repository;

import java.util.Optional;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository <Member, Long> {

    Optional<Member> findMemberBySocialLoginIdAndSocialLoginType(String socialLoginId, SocialLoginType type);
}
