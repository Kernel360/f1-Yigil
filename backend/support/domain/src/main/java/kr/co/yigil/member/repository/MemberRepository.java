package kr.co.yigil.member.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;

public interface MemberRepository extends JpaRepository <Member, Long> {

    Optional<Member> findMemberBySocialLoginIdAndSocialLoginType(String socialLoginId, SocialLoginType type);

    Optional<Member> findMemberByEmailAndSocialLoginType(String email, SocialLoginType type);

    @Query(value = "SELECT m.* FROM Member m WHERE m.id = :memberId", nativeQuery = true)
    Optional<Member> findByIdRegardlessOfStatus(Long memberId);

    @Query(value = "SELECT m.* FROM Member m ORDER BY m.joined_at DESC", nativeQuery = true)
    Page<Member> findAllMembersRegardlessOfStatus(Pageable pageable);

    @Modifying
    @Query("UPDATE Member m SET m.status = 'BANNED' WHERE m.id = :memberId")
    void banMemberById(@Param("memberId") Long memberId);
    @Modifying
    @Query(value = "UPDATE member SET status = 'ACTIVE' WHERE id = :memberId", nativeQuery = true)
    void unbanMemberById(@Param("memberId") Long memberId);

	boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}
