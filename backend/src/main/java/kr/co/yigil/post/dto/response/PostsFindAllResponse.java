package kr.co.yigil.post.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostsFindAllResponse {
    private List<PostResponse> posts;

    public static PostsFindAllResponse from(List<PostResponse> posts) {
        return new PostsFindAllResponse(posts);
    }
}
