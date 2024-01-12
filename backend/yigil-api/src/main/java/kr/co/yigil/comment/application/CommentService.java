package kr.co.yigil.comment.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_COMMENT_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.comment.dto.request.CommentCreateRequest;
import kr.co.yigil.comment.dto.response.CommentCreateResponse;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.travel.application.TravelService;
import kr.co.yigil.travel.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final TravelService travelService;

    @Transactional
    public CommentCreateResponse createComment(Long memberId, Long travelId, CommentCreateRequest commentCreateRequest) {
        Member member = memberService.findMemberById(memberId);
        Travel travel = travelService.findTravelById(travelId);
        Comment newComment = CommentCreateRequest.toEntity(commentCreateRequest, member, travel);

        if (commentCreateRequest.getParentId() != null) {
            Comment parentComment = commentRepository.findById(commentCreateRequest.getParentId())
                    .orElseThrow(() -> new BadRequestException(NOT_FOUND_COMMENT_ID));
            // todo : 대댓글 알림 보내기 (notifiedMemberId로)
            newComment.setParent(parentComment);
        }
        commentRepository.save(newComment);
        return new CommentCreateResponse("댓글 생성 성공");
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentList(Long travelId) {
        List<Comment> comments = commentRepository.findByTravelId(travelId);

        List<CommentResponse> commentResponses = new ArrayList<>();
        Map<Long, CommentResponse> commentResponseMap = new HashMap<>();

        comments.forEach(comment -> {
            CommentResponse commentResponse = CommentResponse.from(comment);
            commentResponseMap.put(commentResponse.getId(), commentResponse);
            if (comment.getParent() != null) {
                commentResponseMap.get(comment.getParent().getId()).getChildren().add(commentResponse);
            } else {
                commentResponses.add(commentResponse);
            }
        });

        return commentResponses;
    }

}

