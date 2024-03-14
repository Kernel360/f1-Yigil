package kr.co.yigil.report.report.intefaces.controller;


import kr.co.yigil.report.report.application.ReportFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/reports")
@RestController
@RequiredArgsConstructor
public class ReportApiController {
    private final ReportFacade reportFacade;


}
