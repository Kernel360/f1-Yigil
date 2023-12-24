package kr.co.yigil.post.application;

import java.util.List;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostListResponse findAllPosts() { // querydsl? 검색 쿼리 추가
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream().map(PostResponse::from).toList();
        return PostListResponse.from(postResponses);
    }
    //Follower 객체 생성 이후 구현
//    public PostListResponse findFollowerPosts() {
//
//    }
    // 생성 response, request 별도 생성
    public PostResponse createPost(Member member, PostCreateRequest postcreateRequest) {
        return PostCreateResponse.ok;
    }
    // 업데이트 response, request 별도 생성
    public PostResponse updatePost(Member member, PostUpdateRequest postRequest) {
        return PostUpdateResponse;
    }
    // 삭제 response, request 별도 생성
    public PostResponse deletePost(Member member, PostDeleteRequest postRequest) {

        return PostDeleteResponse;
    }

}