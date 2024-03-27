package kr.co.yigil.comment.interfaces.dto.mapper;


import kr.co.yigil.comment.domain.CommentCommand;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.interfaces.dto.CommentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentMapper {

    //request
    CommentCommand.CommentCreateRequest of(CommentDto.CommentCreateRequest commentCreateRequest);

    CommentCommand.CommentUpdateRequest of(CommentDto.CommentUpdateRequest commentUpdateRequest);

    //response

    CommentDto.CommentsResponse of(CommentInfo.CommentsResponse commentsResponse);
    CommentDto.CommentsUnitInfo of(CommentInfo.CommentsUnitInfo commentsUnitInfo);

    CommentDto.CommentDeleteResponse of(CommentInfo.DeleteResponse commentDeleteResponse);


}
