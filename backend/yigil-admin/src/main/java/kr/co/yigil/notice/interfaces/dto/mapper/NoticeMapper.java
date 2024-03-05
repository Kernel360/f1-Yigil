package kr.co.yigil.notice.interfaces.dto.mapper;

import kr.co.yigil.notice.domain.NoticeCommand;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeDetail;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeItem;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import kr.co.yigil.notice.interfaces.dto.NoticeDto;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeCreateRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface NoticeMapper {
    @Mapping(target="noticeList", source="noticeList")
    NoticeDto.NoticeListResponse toDto(NoticeListInfo response);

    NoticeDto.NoticeItem of (NoticeItem noticeItem);

    NoticeDto.NoticeDetailResponse toDto(NoticeDetail response);

    NoticeCommand.NoticeCreateRequest toCommand(NoticeCreateRequest request);

    NoticeCommand.NoticeUpdateRequest toCommand(NoticeDto.NoticeUpdateRequest request);
}
