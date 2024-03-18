package kr.co.yigil.report.report.interfaces.controller;


import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.report.report.application.ReportFacade;
import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import kr.co.yigil.report.report.interfaces.dto.ReportDto;
import kr.co.yigil.report.report.interfaces.dto.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportApiController {
    private final ReportFacade reportFacade;
    private final ReportMapper requestMapper;

    @PostMapping
    @MemberOnly
    public ResponseEntity<ReportDto.ReportResponse> submitReport(
            @RequestBody ReportDto.TravelReportCreateRequest request,
            @Auth Accessor accessor
    ) {
        ReportCommand.CreateCommand command = requestMapper.toCommand(request, accessor.getMemberId());
        reportFacade.submitReport(command);
        return ResponseEntity.ok(new ReportDto.ReportResponse("신고글이 등록되었습니다."));
    }

    @GetMapping
    @MemberOnly
    public ResponseEntity<ReportDto.MyReportsResponse> getMyReports(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) String keyword,
            @Auth Accessor accessor
    ) {

        ReportInfo.ReportsInfo reports = reportFacade.getMemberReportListInfo(accessor.getMemberId(), typeId, keyword, pageable);
        ReportDto.MyReportsResponse response = requestMapper.toMyReportsResponse(reports);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @MemberOnly
    public ResponseEntity<ReportDto.MyReportDetail> getMyReportDetail(
            @PathVariable Long id,
            @Auth Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        ReportInfo.ReportDetailInfo report = reportFacade.getMemberReportInfo(memberId, id);
        ReportDto.MyReportDetail response = requestMapper.toMyReportDetail(report);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @MemberOnly
    public ResponseEntity<ReportDto.ReportResponse> deleteMyReport(
            @PathVariable Long id,
            @Auth Accessor accessor
    ) {
        reportFacade.deleteReport(accessor.getMemberId(), id);
        return ResponseEntity.ok(new ReportDto.ReportResponse("신고글이 삭제되었습니다."));
    }

    @GetMapping("/types")
    @MemberOnly
    public ResponseEntity<ReportDto.ReportTypeResponse> getReportTypes() {
        ReportInfo.ReportTypesInfo reportType = reportFacade.getReportTypes();
        ReportDto.ReportTypeResponse response = requestMapper.toDto(reportType);
        return ResponseEntity.ok(response);
    }
}
