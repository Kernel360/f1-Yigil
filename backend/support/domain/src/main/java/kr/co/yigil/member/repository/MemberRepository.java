package kr.co.yigil.member.repository;

import java.util.Optional;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member, Long> {

    Optional<Member> findMemberBySocialLoginIdAndSocialLoginType(String socialLoginId, SocialLoginType type);
}
