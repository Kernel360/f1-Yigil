package kr.co.yigil.post.application;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.post.dto.request.PostCreateRequest;
import kr.co.yigil.post.dto.request.PostDeleteRequest;
import kr.co.yigil.post.dto.request.PostUpdateRequest;
import kr.co.yigil.post.dto.response.PostCreateResponse;
import kr.co.yigil.post.dto.response.PostDeleteResponse;
import kr.co.yigil.post.dto.response.PostListResponse;
import kr.co.yigil.post.dto.response.PostResponse;
import kr.co.yigil.post.dto.response.PostUpdateResponse;
import kr.co.yigil.post.util.PostUtils;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import kr.co.yigil.travel.dto.response.SpotDeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostUtils postUtils;
    private final TravelRepository travelRepository;

    public PostListResponse findAllPosts() { // querydsl? 검색 쿼리 추가
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream().map(PostResponse::from).toList();
        return PostListResponse.from(postResponses);
    }

    @Transactional
    public PostDeleteResponse deletePost(Long memberId, Long postId) {
        Post post = postUtils.findPostById(postId);
        postUtils.validatePostWriter(memberId, postId);
        Travel travel = post.getTravel();
        travelRepository.delete(travel);
        postRepository.delete(post);
        return new PostDeleteResponse("post 삭제 성공");
    }
}