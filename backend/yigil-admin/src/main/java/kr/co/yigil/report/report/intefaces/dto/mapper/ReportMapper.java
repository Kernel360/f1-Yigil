package kr.co.yigil.report.report.intefaces.dto.mapper;

import kr.co.yigil.report.report.domain.ReportCommand;
import kr.co.yigil.report.report.domain.ReportInfo;
import kr.co.yigil.report.report.intefaces.dto.ReportDto;
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



//    @Mapping(target = "reports", source = "reports")
//    @Mapping(target = "totalPage", source = "totalPage")
    ReportDto.ReportsResponse toReportsResponse(ReportInfo.ReportPageInfo reports) ;

    List<ReportDto.ReportListDto> of(List<ReportInfo.ReportListInfo> reports);


    ReportDto.ReportListDto toReportListInfo(ReportInfo.ReportListInfo infos);

    ReportDto.ReportDetailDto toReportDetailInfo(ReportInfo.ReportDetailInfo info);
    @Mapping(target = "reportId", source = "id")
    @Mapping(target = "content", source = "report.content")
    ReportCommand.ReportReplyCommand toReportReplyCommand(Long id, ReportDto.ReportReplyRequest report);
}
