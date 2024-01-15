package kr.co.yigil.like.domain.repository;

import kr.co.yigil.like.domain.Like;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    public int countByPostId(Long postId);

    public void deleteByMemberAndPost(Member member, Post post);
}
