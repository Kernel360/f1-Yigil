package kr.co.yigil.post.util;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostUtils {
    private final PostRepository postRepository;

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_POST_ID)
        );
    }

    public void validatePostWriter(Long memberId, Long postId) {
        if (!postRepository.existsByMemberIdAndId(memberId, postId)) {
            throw new BadRequestException(ExceptionCode.INVALID_AUTHORITY);
        }
    }
}
