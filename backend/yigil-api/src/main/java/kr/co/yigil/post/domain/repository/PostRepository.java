package kr.co.yigil.domain.repository;

import java.util.List;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long> {

    List<Post> findAllByMember(Member member);
}
