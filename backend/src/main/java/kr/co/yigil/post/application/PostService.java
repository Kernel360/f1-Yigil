package kr.co.yigil.post.application;

import java.util.List;

import kr.co.yigil.post.dto.response.PostsFindAllResponse;
import kr.co.yigil.post.dto.response.PostResponse;
import kr.co.yigil.travel.domain.repository.CourseRepository;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import org.springframework.stereotype.Service;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final TravelRepository travelRepository;
    private final MemberRepository memberRepository;
    private final SpotRepository spotRepository;
    private final CourseRepository courseRepository;

    public PostsFindAllResponse findAllPosts() { // 추후에 검색쿼리 추가
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream().map(PostResponse::from).toList();
        return PostsFindAllResponse.from(postResponses);
    }

    // todo 삭제로직 지금은 spot, course 에 travel따로 , post 따로 삭제됐는데 cascade 써서 한번에 삭제가능 할수 있지 아늘까........






    public Member findMemberbyId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID)
        );
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                // todo: custome error code 정의
                () -> new RuntimeException("해당하는 포스트가 없습니다.")
        );
    }





}
