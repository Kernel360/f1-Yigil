package kr.co.yigil.report.report.interfaces.dto.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import kr.co.yigil.report.report.interfaces.dto.ReportDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T00:24:08+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ReportMapperImpl implements ReportMapper {

    @Override
    public ReportCommand.CreateCommand toCommand(ReportDto.TravelReportCreateRequest request, Long reporterId) {
        if ( request == null && reporterId == null ) {
            return null;
        }

        ReportCommand.CreateCommand.CreateCommandBuilder createCommand = ReportCommand.CreateCommand.builder();

        if ( request != null ) {
            createCommand.reportTypeId( request.getReportTypeId() );
            createCommand.content( request.getContent() );
            createCommand.travelId( request.getTravelId() );
        }
        createCommand.reporterId( reporterId );

        return createCommand.build();
    }

    @Override
    public ReportDto.MyReportsResponse toMyReportsResponse(ReportInfo.ReportsInfo reports) {
        if ( reports == null ) {
            return null;
        }

        List<ReportDto.MyReportDetail> myReports = null;
        int totalPage = 0;

        myReports = of( reports.getReports() );
        totalPage = reports.getTotalPage();

        ReportDto.MyReportsResponse myReportsResponse = new ReportDto.MyReportsResponse( myReports, totalPage );

        return myReportsResponse;
    }

    @Override
    public List<ReportDto.MyReportDetail> of(List<ReportInfo.ReportListInfo> reportDetails) {
        if ( reportDetails == null ) {
            return null;
        }

        List<ReportDto.MyReportDetail> list = new ArrayList<ReportDto.MyReportDetail>( reportDetails.size() );
        for ( ReportInfo.ReportListInfo reportListInfo : reportDetails ) {
            list.add( reportListInfoToMyReportDetail( reportListInfo ) );
        }

        return list;
    }

    @Override
    public ReportDto.MyReportDetail toMyReportDetail(ReportInfo.ReportDetailInfo report) {
        if ( report == null ) {
            return null;
        }

        Long id = null;
        ReportDto.ReportTypeDto reportType = null;
        String createdAt = null;

        id = report.getId();
        reportType = reportTypeInfoToReportTypeDto( report.getReportType() );
        if ( report.getCreatedAt() != null ) {
            createdAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( report.getCreatedAt() );
        }

        ReportDto.MyReportDetail myReportDetail = new ReportDto.MyReportDetail( id, reportType, createdAt );

        return myReportDetail;
    }

    @Override
    public ReportDto.ReportTypeResponse toDto(ReportInfo.ReportTypesInfo reportType) {
        if ( reportType == null ) {
            return null;
        }

        ReportDto.ReportTypeResponse reportTypeResponse = new ReportDto.ReportTypeResponse();

        reportTypeResponse.setReportTypes( toDto( reportType.getReportTypes() ) );

        return reportTypeResponse;
    }

    @Override
    public List<ReportDto.ReportTypeDto> toDto(List<ReportInfo.ReportTypeDetail> reportTypes) {
        if ( reportTypes == null ) {
            return null;
        }

        List<ReportDto.ReportTypeDto> list = new ArrayList<ReportDto.ReportTypeDto>( reportTypes.size() );
        for ( ReportInfo.ReportTypeDetail reportTypeDetail : reportTypes ) {
            list.add( reportTypeDetailToReportTypeDto( reportTypeDetail ) );
        }

        return list;
    }

    protected ReportDto.ReportTypeDto reportTypeInfoToReportTypeDto(ReportInfo.ReportTypeInfo reportTypeInfo) {
        if ( reportTypeInfo == null ) {
            return null;
        }

        ReportDto.ReportTypeDto reportTypeDto = new ReportDto.ReportTypeDto();

        reportTypeDto.setId( reportTypeInfo.getId() );
        reportTypeDto.setName( reportTypeInfo.getName() );

        return reportTypeDto;
    }

    protected ReportDto.MyReportDetail reportListInfoToMyReportDetail(ReportInfo.ReportListInfo reportListInfo) {
        if ( reportListInfo == null ) {
            return null;
        }

        Long id = null;
        ReportDto.ReportTypeDto reportType = null;
        String createdAt = null;

        id = reportListInfo.getId();
        reportType = reportTypeInfoToReportTypeDto( reportListInfo.getReportType() );
        if ( reportListInfo.getCreatedAt() != null ) {
            createdAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( reportListInfo.getCreatedAt() );
        }

        ReportDto.MyReportDetail myReportDetail = new ReportDto.MyReportDetail( id, reportType, createdAt );

        return myReportDetail;
    }

    protected ReportDto.ReportTypeDto reportTypeDetailToReportTypeDto(ReportInfo.ReportTypeDetail reportTypeDetail) {
        if ( reportTypeDetail == null ) {
            return null;
        }

        ReportDto.ReportTypeDto reportTypeDto = new ReportDto.ReportTypeDto();

        reportTypeDto.setId( reportTypeDetail.getId() );
        reportTypeDto.setName( reportTypeDetail.getName() );

        return reportTypeDto;
    }
}
