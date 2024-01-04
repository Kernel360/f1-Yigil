package kr.co.yigil.post.presentation;

import jakarta.persistence.Access;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.dto.response.PostDeleteResponse;
import kr.co.yigil.post.dto.response.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;


    @GetMapping
    public ResponseEntity<PostListResponse> findAllPosts() {
        PostListResponse posts = postService.findAllPosts();
        return ResponseEntity.ok().body(posts);
    }

    @DeleteMapping("/{post_id}")
    @MemberOnly
    public ResponseEntity<PostDeleteResponse> deletePost(
        @PathVariable("post_id") Long postId,
        @Auth final Accessor accessor
    ) {
        PostDeleteResponse postDeleteResponse = postService.deletePost(accessor.getMemberId(), postId);
//        PostDeleteResponse postDeleteResponse = postService.deletePost(1L, postId);
        return ResponseEntity.ok().body(postDeleteResponse);
    }
    // todo: spot list 리턴해주는 엔드포인트 정의



}