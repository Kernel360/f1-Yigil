package kr.co.yigil.notice.interfaces.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.notice.domain.NoticeCommand;
import kr.co.yigil.notice.domain.NoticeInfo;
import kr.co.yigil.notice.interfaces.dto.NoticeDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T16:25:42+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class NoticeMapperImpl implements NoticeMapper {

    @Override
    public NoticeDto.NoticeListResponse toDto(NoticeInfo.NoticeListInfo response) {
        if ( response == null ) {
            return null;
        }

        NoticeDto.NoticeListResponse.NoticeListResponseBuilder noticeListResponse = NoticeDto.NoticeListResponse.builder();

        noticeListResponse.noticeList( noticeItemListToNoticeItemList( response.getNoticeList() ) );
        noticeListResponse.hasNext( response.isHasNext() );

        return noticeListResponse.build();
    }

    @Override
    public NoticeDto.NoticeItem of(NoticeInfo.NoticeItem noticeItem) {
        if ( noticeItem == null ) {
            return null;
        }

        NoticeDto.NoticeItem.NoticeItemBuilder noticeItem1 = NoticeDto.NoticeItem.builder();

        noticeItem1.id( noticeItem.getId() );
        noticeItem1.title( noticeItem.getTitle() );
        noticeItem1.content( noticeItem.getContent() );
        noticeItem1.author( noticeItem.getAuthor() );
        noticeItem1.createdAt( noticeItem.getCreatedAt() );
        noticeItem1.updatedAt( noticeItem.getUpdatedAt() );

        return noticeItem1.build();
    }

    @Override
    public NoticeDto.NoticeDetailResponse toDto(NoticeInfo.NoticeDetail response) {
        if ( response == null ) {
            return null;
        }

        NoticeDto.NoticeDetailResponse.NoticeDetailResponseBuilder noticeDetailResponse = NoticeDto.NoticeDetailResponse.builder();

        noticeDetailResponse.id( response.getId() );
        noticeDetailResponse.title( response.getTitle() );
        noticeDetailResponse.content( response.getContent() );
        noticeDetailResponse.createdAt( response.getCreatedAt() );
        noticeDetailResponse.updatedAt( response.getUpdatedAt() );

        return noticeDetailResponse.build();
    }

    @Override
    public NoticeCommand.NoticeCreateRequest toCommand(NoticeDto.NoticeCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        NoticeCommand.NoticeCreateRequest.NoticeCreateRequestBuilder noticeCreateRequest = NoticeCommand.NoticeCreateRequest.builder();

        noticeCreateRequest.author( request.getAuthor() );
        noticeCreateRequest.title( request.getTitle() );
        noticeCreateRequest.content( request.getContent() );

        return noticeCreateRequest.build();
    }

    @Override
    public NoticeCommand.NoticeUpdateRequest toCommand(NoticeDto.NoticeUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        NoticeCommand.NoticeUpdateRequest.NoticeUpdateRequestBuilder noticeUpdateRequest = NoticeCommand.NoticeUpdateRequest.builder();

        noticeUpdateRequest.author( request.getAuthor() );
        noticeUpdateRequest.title( request.getTitle() );
        noticeUpdateRequest.content( request.getContent() );

        return noticeUpdateRequest.build();
    }

    protected List<NoticeDto.NoticeItem> noticeItemListToNoticeItemList(List<NoticeInfo.NoticeItem> list) {
        if ( list == null ) {
            return null;
        }

        List<NoticeDto.NoticeItem> list1 = new ArrayList<NoticeDto.NoticeItem>( list.size() );
        for ( NoticeInfo.NoticeItem noticeItem : list ) {
            list1.add( of( noticeItem ) );
        }

        return list1;
    }
}
