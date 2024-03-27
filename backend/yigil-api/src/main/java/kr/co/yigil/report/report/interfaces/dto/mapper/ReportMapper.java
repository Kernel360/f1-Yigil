package kr.co.yigil.report.report.interfaces.dto.mapper;


import kr.co.yigil.report.report.domain.ReportTarget;
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

    @Mapping(target = "reportTypeId", source = "request.reportTypeId")
    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "targetId", source = "request.targetId")
    @Mapping(target = "targetType", source = "request.targetType")
    ReportCommand.CreateCommand toCommand(ReportDto.TravelReportCreateRequest request, Long reporterId);

    default ReportTarget toReportTarget(String source){
        return ReportTarget.valueOf(source.toUpperCase());
    }


    @Mapping(target = "myReports", source = "reports")
    ReportDto.MyReportsResponse toMyReportsResponse(ReportInfo.ReportsInfo reports);

    List<ReportDto.MyReportDetail> of(List<ReportInfo.ReportListInfo> reportDetails);

    ReportDto.MyReportDetail toMyReportDetail(ReportInfo.ReportDetailInfo report);

    ReportDto.ReportTypeResponse toDto(ReportInfo.ReportTypesInfo reportType);

    List<ReportDto.ReportTypeDto> toDto(List<ReportInfo.ReportTypeDetail> reportTypes);
}
