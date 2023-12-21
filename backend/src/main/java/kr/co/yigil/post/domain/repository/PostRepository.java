package kr.co.yigil.post.domain.repository;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    

    void deleteByTravelId(Long id);

    List<Post> findAllByMember(Member member);
}
