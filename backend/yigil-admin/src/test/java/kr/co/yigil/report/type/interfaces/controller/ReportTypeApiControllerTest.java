package kr.co.yigil.report.type.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.co.yigil.report.type.application.ReportTypeFacade;
import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.ReportTypeCreateRequest;
import kr.co.yigil.report.type.interfaces.dto.ReportTypeDto.ReportTypeListResponse;
import kr.co.yigil.report.type.interfaces.dto.mapper.ReportTypeMapper;
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


@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportTypeApiController.class)
class ReportTypeApiControllerTest {

    @MockBean
    private ReportTypeFacade reportTypeFacade;

    @MockBean
    private ReportTypeMapper reportTypeMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .build();
    }


    @DisplayName("신고 유형 목록 조회가 성공하면 OK 상태를 반환한다.")
    @Test
    void whenGetReportTypes_thenShouldReturnOkAndResponse() throws Exception {

        when(reportTypeFacade.getCategories()).thenReturn(mock(List.class));
        when(reportTypeMapper.toDto(any(List.class))).thenReturn(mock(
            ReportTypeListResponse.class));

        mockMvc.perform(get("/api/v1/report-types"))
            .andExpect(status().isOk());

    }

    @DisplayName("신고 유형이 잘 생성되면 OK 상태를 반환한다.")
    @Test
    void whenCreateReportType_thenShouldReturnOkAndResponse() throws Exception {
        CreateReportType command = mock(CreateReportType.class);
        when(reportTypeMapper.toCommand(any(ReportTypeCreateRequest.class))).thenReturn(command);
        String request = """
            {
                "name": "name",
                "description": "description"
            }
                        
            """;

        mockMvc.perform(post("/api/v1/report-types")
                .contentType("application/json")
                .content(request)
            )
            .andExpect(status().isOk());

        verify(reportTypeFacade).createReportType(command);
    }

    @DisplayName("신고 유형이 잘 삭제되면 OK 상태를 반환한다.")
    @Test
    void whenDeleteReportType_thenShouldReturnOkAndResponse() throws Exception {
        Long typeId = 1L;
        mockMvc.perform(delete("/api/v1/report-types/{typeId}", typeId))
            .andExpect(status().isOk());
        verify(reportTypeFacade).deleteReportType(typeId);
    }
}