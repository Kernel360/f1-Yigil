package kr.co.yigil.report.report.intefaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import kr.co.yigil.report.report.application.ReportFacade;
import kr.co.yigil.report.report.domain.ReportCommand;
import kr.co.yigil.report.report.domain.ReportInfo;
import kr.co.yigil.report.report.intefaces.dto.ReportDto;
import kr.co.yigil.report.report.intefaces.dto.mapper.ReportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportApiController.class)
class ReportApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ReportFacade reportFacade;

    @MockBean
    private ReportMapper reportMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("신고글 등록 요청이 오면 200 응답과 함께 신고글 등록 성공 메시지를 반환한다.")
    @Test
    void whenGetReports_thenShouldReturn200AndResponse() throws Exception {
        List<ReportDto.ReportListDto> reports = List.of(
                mock(ReportDto.ReportListDto.class),
                mock(ReportDto.ReportListDto.class)
        );

        ReportDto.ReportsResponse reportsResponse = ReportDto.ReportsResponse.builder()
                .reports(reports)
                .totalPage(1)
                .build();

        when(reportFacade.getReports(any(), anyLong(), anyLong(), anyString())).thenReturn(mock(ReportInfo.ReportPageInfo.class));
        when(reportMapper.toReportsResponse(any())).thenReturn(reportsResponse);

        mockMvc.perform(get("/api/v1/reports")
                        .param("memberId", "1")
                        .param("typeId", "1")
                        .param("keyword", "test")
                )
                .andExpect(status().isOk())
        ;

    }

    @DisplayName("신고글에 답변을 등록하면 200 응답과 함께 답변 등록 성공 메시지를 반환한다.")
    @Test
    void replyToReport() throws Exception {
        ReportDto.ReportReplyRequest report = new ReportDto.ReportReplyRequest("처리되었습니다");
        var command = mock(ReportCommand.ReportReplyCommand.class);
        when(reportMapper.toReportReplyCommand(anyLong(), any())).thenReturn(command);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String content = objectMapper.writeValueAsString(report);

        mockMvc.perform(post("/api/v1/reports/{report_id}", 1)
                        .contentType("application/json")
                        .content(content)
                )
                .andExpect(status().isOk());


        verify(reportFacade).replyToReport(command);

    }

    @DisplayName("신고글을 삭제하면 200 응답과 함께 삭제 성공 메시지를 반환한다.")
    @Test
    void deleteReport() throws Exception {

        mockMvc.perform(delete("/api/v1/reports/{report_id}", 1))
                .andExpect(status().isOk());

        verify(reportFacade).deleteReport(1L);
    }
}