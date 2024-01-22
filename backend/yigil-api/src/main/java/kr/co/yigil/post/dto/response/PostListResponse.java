package kr.co.yigil.post.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostListResponse {
    private List<PostResponse> posts;

    public static PostListResponse from(List<PostResponse> posts) {
        return new PostListResponse(posts);
    }
}