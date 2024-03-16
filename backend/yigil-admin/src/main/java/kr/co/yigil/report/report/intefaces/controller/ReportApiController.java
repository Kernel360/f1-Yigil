package kr.co.yigil.report.report.intefaces.controller;


import kr.co.yigil.report.report.application.ReportFacade;
import kr.co.yigil.report.report.domain.ReportCommand;
import kr.co.yigil.report.report.domain.ReportInfo;
import kr.co.yigil.report.report.intefaces.dto.ReportDto;
import kr.co.yigil.report.report.intefaces.dto.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/reports")
@RestController
@RequiredArgsConstructor
public class ReportApiController {
    private final ReportFacade reportFacade;
    private final ReportMapper reportMapper;

    @GetMapping
    public ResponseEntity<ReportDto.ReportsResponse> getReports(
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) String keyword
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
        ReportInfo.ReportPageInfo reports = reportFacade.getReports(pageRequest, memberId, typeId, keyword);
        ReportDto.ReportsResponse response = reportMapper.toReportsResponse(reports);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDto.ProcessResultResponse> replyToReport(
            @PathVariable Long id,
            @RequestBody ReportDto.ReportReplyRequest report
    ) {
        ReportCommand.ReportReplyCommand command = reportMapper.toReportReplyCommand(id, report);
        reportFacade.replyToReport(command);
        ReportDto.ProcessResultResponse response = new ReportDto.ProcessResultResponse("신고에 응답을 완료하였습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReportDto.ProcessResultResponse> deleteReport(
            @PathVariable Long id
    ) {
        reportFacade.deleteReport(id);
        ReportDto.ProcessResultResponse response = new ReportDto.ProcessResultResponse("success");
        return ResponseEntity.ok(response);
    }
}
