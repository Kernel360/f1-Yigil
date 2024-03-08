package kr.co.yigil.comment.infterfaces.dto.mapper;

import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.domain.CommentInfo.ChildrenPageComments;
import kr.co.yigil.comment.domain.CommentInfo.ParentPageComments;
import kr.co.yigil.comment.infterfaces.dto.CommentDto;
import kr.co.yigil.comment.infterfaces.dto.CommentDto.ChildrenCommentsResponse;
import kr.co.yigil.comment.infterfaces.dto.CommentDto.ParentCommentsResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentMapper {

    default ParentCommentsResponse of(ParentPageComments info){
        return new ParentCommentsResponse(
            info.getParentComments().map(this::ofParentCommentInfo)
        );
    }

    default ChildrenCommentsResponse of(ChildrenPageComments info){
        return new ChildrenCommentsResponse(
            info.getChildrenComments().map(this::ofChildrenCommentInfo)
        );
    }

    default CommentDto.ParentCommentsInfo ofParentCommentInfo(CommentInfo.ParentListInfo info){
        return new CommentDto.ParentCommentsInfo(
            info.getId(),
            info.getMemberNickname(),
            info.getMemberId(),
            info.getContent(),
            info.getCreatedAt(),
            info.getChildrenCount()
        );
    }

    default CommentDto.ChildrenCommentsInfo ofChildrenCommentInfo(CommentInfo.ChildrenListInfo info){
        return new CommentDto.ChildrenCommentsInfo(
            info.getId(),
            info.getMemberNickname(),
            info.getMemberId(),
            info.getContent(),
            info.getCreatedAt()
        );
    }
}
