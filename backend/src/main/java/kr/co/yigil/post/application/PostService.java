package kr.co.yigil.post.application;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.post.dto.response.PostDeleteResponse;
import kr.co.yigil.post.dto.response.PostListResponse;
import kr.co.yigil.post.dto.response.PostResponse;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final TravelRepository travelRepository;

    @Transactional(readOnly = true)
    public PostListResponse findAllPosts() { // querydsl? 검색 쿼리 추가
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream().map(PostResponse::from).toList();
        return PostListResponse.from(postResponses);
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_POST_ID)
        );
    }

    @Transactional
    public void createPost(Travel travel, Member member) {
        postRepository.save(new Post(travel, member));
    }

    @Transactional
    public void recreatePost(Long memberId, Long spotId) {
        Post post = postRepository.findByMemberIdAndTravelId(memberId, spotId)
            .orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_POST_ID)
            );
        post.setIsDeleted(false);
    }

    @Transactional
    public void updatePost(Long postId, Travel travel, Member member) {
        postRepository.save(new Post(postId, travel, member));
    }

    @Transactional
    public PostDeleteResponse deletePost(Long memberId, Long postId) {
        Post post = findPostById(postId);
        validatePostWriter(memberId, postId);
        Travel travel = post.getTravel();
        travelRepository.delete(travel);
        postRepository.delete(post);
//        post.setIsDeleted(true);
        return new PostDeleteResponse("post 삭제 성공");
    }

    @Transactional
    public void deleteOnlyPost(Long memberId, Long travelId) {
        Post post = postRepository.findByMemberIdAndTravelId(memberId, travelId)
            .orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_POST_ID)
            );
        post.setIsDeleted(true);
    }

    @Transactional(readOnly = true)
    public void validatePostWriter(Long memberId, Long postId) {
        if (!postRepository.existsByMemberIdAndId(memberId, postId)) {
            throw new BadRequestException(ExceptionCode.INVALID_AUTHORITY);
        }
    }

}