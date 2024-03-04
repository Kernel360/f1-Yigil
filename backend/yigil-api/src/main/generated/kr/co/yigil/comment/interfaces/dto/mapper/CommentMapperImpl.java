package kr.co.yigil.comment.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.comment.domain.CommentCommand;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.interfaces.dto.CommentDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-04T18:24:58+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentCommand.CommentCreateRequest of(CommentDto.CommentCreateRequest commentCreateRequest) {
        if ( commentCreateRequest == null ) {
            return null;
        }

        CommentCommand.CommentCreateRequest.CommentCreateRequestBuilder commentCreateRequest1 = CommentCommand.CommentCreateRequest.builder();

        commentCreateRequest1.content( commentCreateRequest.getContent() );
        commentCreateRequest1.parentId( commentCreateRequest.getParentId() );

        return commentCreateRequest1.build();
    }

    @Override
    public CommentCommand.CommentUpdateRequest of(CommentDto.CommentUpdateRequest commentUpdateRequest) {
        if ( commentUpdateRequest == null ) {
            return null;
        }

        CommentCommand.CommentUpdateRequest.CommentUpdateRequestBuilder commentUpdateRequest1 = CommentCommand.CommentUpdateRequest.builder();

        commentUpdateRequest1.content( commentUpdateRequest.getContent() );

        return commentUpdateRequest1.build();
    }

    @Override
    public CommentDto.CommentsResponse of(CommentInfo.CommentsResponse commentsResponse) {
        if ( commentsResponse == null ) {
            return null;
        }

        CommentDto.CommentsResponse commentsResponse1 = new CommentDto.CommentsResponse();

        commentsResponse1.setContent( commentsUnitInfoListToCommentsUnitInfoList( commentsResponse.getContent() ) );
        commentsResponse1.setHasNext( commentsResponse.isHasNext() );

        return commentsResponse1;
    }

    @Override
    public CommentDto.CommentDeleteResponse of(CommentInfo.DeleteResponse commentDeleteResponse) {
        if ( commentDeleteResponse == null ) {
            return null;
        }

        String message = null;

        message = commentDeleteResponse.message();

        CommentDto.CommentDeleteResponse commentDeleteResponse1 = new CommentDto.CommentDeleteResponse( message );

        return commentDeleteResponse1;
    }

    protected CommentDto.CommentsUnitInfo commentsUnitInfoToCommentsUnitInfo(CommentInfo.CommentsUnitInfo commentsUnitInfo) {
        if ( commentsUnitInfo == null ) {
            return null;
        }

        CommentDto.CommentsUnitInfo commentsUnitInfo1 = new CommentDto.CommentsUnitInfo();

        commentsUnitInfo1.setId( commentsUnitInfo.getId() );
        commentsUnitInfo1.setContent( commentsUnitInfo.getContent() );
        commentsUnitInfo1.setMemberId( commentsUnitInfo.getMemberId() );
        commentsUnitInfo1.setMemberNickname( commentsUnitInfo.getMemberNickname() );
        commentsUnitInfo1.setMemberImageUrl( commentsUnitInfo.getMemberImageUrl() );
        commentsUnitInfo1.setChildCount( commentsUnitInfo.getChildCount() );
        commentsUnitInfo1.setCreatedAt( commentsUnitInfo.getCreatedAt() );

        return commentsUnitInfo1;
    }

    protected List<CommentDto.CommentsUnitInfo> commentsUnitInfoListToCommentsUnitInfoList(List<CommentInfo.CommentsUnitInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto.CommentsUnitInfo> list1 = new ArrayList<CommentDto.CommentsUnitInfo>( list.size() );
        for ( CommentInfo.CommentsUnitInfo commentsUnitInfo : list ) {
            list1.add( commentsUnitInfoToCommentsUnitInfo( commentsUnitInfo ) );
        }

        return list1;
    }
}
