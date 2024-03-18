package kr.co.yigil.report.type.interfaces.controller;

import java.util.List;
import kr.co.yigil.report.type.application.ReportTypeFacade;
import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.ReportTypeCreateRequest;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.ReportTypeResponse;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.ReportTypeListResponse;
import kr.co.yigil.report.type.interfaces.dto.mapper.ReportTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report-types")
@RequiredArgsConstructor
public class ReportTypeApiController {

    private final ReportTypeFacade reportTypeFacade;
    private final ReportTypeMapper reportTypeMapper;

    @GetMapping
    public ResponseEntity<ReportTypeListResponse> getReportTypes() {

        List<ReportType> types = reportTypeFacade.getCategories();
        ReportTypeListResponse response = reportTypeMapper.toDto(types);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReportTypeResponse> createReportType(
        @RequestBody ReportTypeCreateRequest request
    ) {
        CreateReportType command = reportTypeMapper.toCommand(request);
        reportTypeFacade.createReportType(command);
        return ResponseEntity.ok(new ReportTypeResponse("success"));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<ReportTypeResponse> deleteReportType(
        @PathVariable Long typeId
    ) {
        reportTypeFacade.deleteReportType(typeId);
        return ResponseEntity.ok(new ReportTypeResponse("success"));
    }
}