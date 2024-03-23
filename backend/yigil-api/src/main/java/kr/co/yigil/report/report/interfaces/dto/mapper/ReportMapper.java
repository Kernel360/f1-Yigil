package kr.co.yigil.report.report.interfaces.dto.mapper;


import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import kr.co.yigil.report.report.interfaces.dto.ReportDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReportMapper {
    @Mapping(target = "reporterId", source = "reporterId")
    @Mapping(target = "reportTypeId", source = "request.reportTypeId")
    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "travelId", source = "request.travelId")
    ReportCommand.CreateCommand toCommand(ReportDto.TravelReportCreateRequest request, Long reporterId);

    @Mapping(target = "myReports", source = "reports")
    ReportDto.MyReportsResponse toMyReportsResponse(ReportInfo.ReportsInfo reports);

    List<ReportDto.MyReportDetail> of(List<ReportInfo.ReportListInfo> reportDetails);

    ReportDto.MyReportDetail toMyReportDetail(ReportInfo.ReportDetailInfo report);

    ReportDto.ReportTypeResponse toDto(ReportInfo.ReportTypesInfo reportType);

    List<ReportDto.ReportTypeDto> toDto(List<ReportInfo.ReportTypeDetail> reportTypes);
}
