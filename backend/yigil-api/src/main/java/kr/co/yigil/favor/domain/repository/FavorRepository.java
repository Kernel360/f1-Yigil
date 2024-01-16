package kr.co.yigil.favor.domain.repository;

import kr.co.yigil.favor.domain.Favor;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorRepository extends JpaRepository<Favor, Long> {

    public int countByPostId(Long postId);

    public void deleteByMemberAndPost(Member member, Post post);
}
