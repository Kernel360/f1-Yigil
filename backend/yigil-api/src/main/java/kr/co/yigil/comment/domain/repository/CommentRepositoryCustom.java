package kr.co.yigil.comment.domain.repository;

import java.util.List;
import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.dto.response.CommentResponse;

public interface CommentRepositoryCustom {
    List<Comment> findByTravelId(Long travelId);
    }
