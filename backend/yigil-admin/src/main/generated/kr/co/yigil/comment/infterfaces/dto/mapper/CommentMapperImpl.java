package kr.co.yigil.comment.infterfaces.dto.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.infterfaces.dto.CommentDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T18:10:51+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto.CommentUnitDto of(CommentInfo.CommentListUnit info) {
        if ( info == null ) {
            return null;
        }

        List<CommentDto.ReplyUnitDto> children = null;
        Long id = null;
        String memberNickname = null;
        Long memberId = null;
        String content = null;
        LocalDateTime createdAt = null;

        children = replyListUnitListToReplyUnitDtoList( info.getChildren() );
        id = info.getId();
        memberNickname = info.getMemberNickname();
        memberId = info.getMemberId();
        content = info.getContent();
        createdAt = info.getCreatedAt();

        CommentDto.CommentUnitDto commentUnitDto = new CommentDto.CommentUnitDto( id, memberNickname, memberId, content, createdAt, children );

        return commentUnitDto;
    }

    @Override
    public CommentDto.ReplyUnitDto of(CommentInfo.ReplyListUnit info) {
        if ( info == null ) {
            return null;
        }

        Long id = null;
        String memberNickname = null;
        Long memberId = null;
        String content = null;
        LocalDateTime createdAt = null;

        id = info.getId();
        memberNickname = info.getMemberNickname();
        memberId = info.getMemberId();
        content = info.getContent();
        createdAt = info.getCreatedAt();

        CommentDto.ReplyUnitDto replyUnitDto = new CommentDto.ReplyUnitDto( id, memberNickname, memberId, content, createdAt );

        return replyUnitDto;
    }

    protected List<CommentDto.ReplyUnitDto> replyListUnitListToReplyUnitDtoList(List<CommentInfo.ReplyListUnit> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto.ReplyUnitDto> list1 = new ArrayList<CommentDto.ReplyUnitDto>( list.size() );
        for ( CommentInfo.ReplyListUnit replyListUnit : list ) {
            list1.add( of( replyListUnit ) );
        }

        return list1;
    }
}
