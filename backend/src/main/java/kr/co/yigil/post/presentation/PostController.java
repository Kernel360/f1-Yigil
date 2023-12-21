package kr.co.yigil.post.presentation;

import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.dto.response.PostsFindAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;


    @GetMapping("/posts")
    public ResponseEntity<PostsFindAllResponse> findAllPosts() {
        PostsFindAllResponse posts = postService.findAllPosts();
        return ResponseEntity.ok().body(posts);
    }

    // todo: spot list 리턴해주는 엔드포인트 정의



}
