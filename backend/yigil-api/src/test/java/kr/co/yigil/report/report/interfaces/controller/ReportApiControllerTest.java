package kr.co.yigil.report.report.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import kr.co.yigil.report.report.application.ReportFacade;
import kr.co.yigil.report.report.domain.dto.ReportCommand;
import kr.co.yigil.report.report.domain.dto.ReportInfo;
import kr.co.yigil.report.report.interfaces.dto.ReportDto;
import kr.co.yigil.report.report.interfaces.dto.mapper.ReportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(ReportApiController.class)
@AutoConfigureRestDocs
class ReportApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ReportFacade reportFacade;

    @MockBean
    private ReportMapper requestMapper;

    @BeforeEach
    void setUp(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }


    @DisplayName("신고글 등록 요청이 오면 200 응답과 response가 반환되는지")
    @Test
    void whenSubmitReport_thenShouldReturn200AndResponse() throws Exception {
        ReportCommand.CreateCommand command = mock(ReportCommand.CreateCommand.class);
        when(requestMapper.toCommand(any(), any())).thenReturn(command);

        ReportDto.TravelReportCreateRequest request = ReportDto.TravelReportCreateRequest.builder()
                .reportTypeId(1L)
                .content("content")
                .targetId(1L)
                .targetType("travel")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/reports")
                        .contentType("application/json")
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"신고글이 등록되었습니다.\"}"))
                .andDo(document("reports/submit-report",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("report_type_id").type(JsonFieldType.NUMBER).description("신고 타입 아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("신고 내용:[디자인에 없어서 안보내주셔도 됩니다]"),
                                fieldWithPath("target_id").type(JsonFieldType.NUMBER).description("신고 대상 아이디"),
                                fieldWithPath("target_type").type(JsonFieldType.STRING).description("신고 대상 타입: [travel,comment]")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));


        verify(reportFacade).submitReport(command);
    }

    @DisplayName("신고글 목록 조회 요청이 오면 200 응답과 response가 반환되는지")
    @Test
    void whenGetMyReports_thenShouldReturnAndResponse() throws Exception {

        ReportInfo.ReportsInfo reports = mock(ReportInfo.ReportsInfo.class);
        ReportDto.ReportTypeDto dto1 = new ReportDto.ReportTypeDto();
        dto1.setId(1L);
        dto1.setName("광고성 글");

        ReportDto.ReportTypeDto dto2 = new ReportDto.ReportTypeDto();
        dto2.setId(2L);
        dto2.setName("욕설");
        ReportDto.MyReportDetail myReportDetail1 = new ReportDto.MyReportDetail(
                1L, dto1, LocalDateTime.now().toString()
        );
        ReportDto.MyReportDetail myReportDetail2 = new ReportDto.MyReportDetail(
                2L,
                dto2,
                LocalDateTime.now().toString()
        );

        ReportDto.MyReportsResponse myReportsResponse = new ReportDto.MyReportsResponse(
                List.of(myReportDetail1, myReportDetail2),
                1
        );
        when(reportFacade.getMemberReportListInfo(any(), any(), any(), any())).thenReturn(reports);
        when(requestMapper.toMyReportsResponse(reports)).thenReturn(myReportsResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String result = objectMapper.writeValueAsString(myReportsResponse);


        mockMvc.perform(get("/api/v1/reports")
                        .contentType("application/json")
                        .content("{\"typeId\":1,\"keyword\":\"keyword\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(result))
                .andDo(document("reports/get-my-reports",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("page").description("현재 페이지 - default:1").optional(),
                                parameterWithName("size").description("페이지 크기 - default:5").optional(),
                                parameterWithName("typeId").description("신고 타입 아이디").optional(),
                                parameterWithName("keyword").description("검색 키워드").optional()
                        ),
                        responseFields(
                                subsectionWithPath("my_reports").description("신고글 목록"),
                                fieldWithPath("my_reports[].id").type(JsonFieldType.NUMBER).description("신고글 아이디"),
                                subsectionWithPath("my_reports[].report_type").description("신고 타입"),
                                fieldWithPath("my_reports[].report_type.id").type(JsonFieldType.NUMBER).description("신고 타입 아이디"),
                                fieldWithPath("my_reports[].report_type.name").type(JsonFieldType.STRING).description("신고 타입 이름"),
                                fieldWithPath("my_reports[].created_at").type(JsonFieldType.STRING).description("신고글 생성일"),
                                fieldWithPath("total_page").type(JsonFieldType.NUMBER).description("다음 페이지 존재 여부")
                        )
                ));

    }

    @DisplayName("신고글 상세 조회 요청이 오면 200 응답과 response가 반환되는지")
    @Test
    void getMyReportDetail() throws Exception {

        ReportDto.ReportTypeDto reportType1 = new ReportDto.ReportTypeDto();
        reportType1.setId(1L);
        reportType1.setName("광고성 글");
        ReportDto.MyReportDetail myReportDetail = new ReportDto.MyReportDetail(
                1L,
                reportType1,
                LocalDateTime.now().toString()
        );

        when(reportFacade.getMemberReportInfo(any(), any())).thenReturn(mock(ReportInfo.ReportDetailInfo.class));
        when(requestMapper.toMyReportDetail(any())).thenReturn(myReportDetail);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String result = objectMapper.writeValueAsString(myReportDetail);

        mockMvc.perform(get("/api/v1/reports/{id}",1)
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(result))
                .andDo(document("reports/get-my-report-detail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("신고글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("신고글 아이디"),
                                subsectionWithPath("report_type").description("신고 타입"),
                                fieldWithPath("report_type.id").type(JsonFieldType.NUMBER).description("신고 타입 아이디"),
                                fieldWithPath("report_type.name").type(JsonFieldType.STRING).description("신고 타입 이름"),
                                fieldWithPath("created_at").type(JsonFieldType.STRING).description("신고글 생성일")
                        )
                ));

    }

    @DisplayName("신고글 삭제 요청이 오면 200 응답과 response가 반환되는지")
    @Test
    void deleteMyReport() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String result = objectMapper.writeValueAsString(new ReportDto.ReportResponse("신고글이 삭제되었습니다."));

        mockMvc.perform(delete("/api/v1/reports/{report_id}", 1)
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(result))
                .andDo(document("reports/delete-my-report",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("report_id").description("삭제할 신고글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));

    }

    @DisplayName("신고 타입 목록 조회 요청이 오면 200 응답과 response가 반환되는지")
    @Test
    void getReportTypes() throws Exception {

        ReportDto.ReportTypeDto dto1 = new ReportDto.ReportTypeDto();
        dto1.setId(1L);
        dto1.setName("광고성 글");
        ReportDto.ReportTypeDto dto2 = new ReportDto.ReportTypeDto();
        dto2.setId(2L);
        dto2.setName("욕설");
        ReportDto.ReportTypeResponse reportTypeResponse = new ReportDto.ReportTypeResponse();
        reportTypeResponse.setReportTypes(List.of(dto1, dto2));

        when(reportFacade.getReportTypes()).thenReturn(mock(ReportInfo.ReportTypesInfo.class));
        when(requestMapper.toDto(any(ReportInfo.ReportTypesInfo.class))).thenReturn(reportTypeResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String result = objectMapper.writeValueAsString(reportTypeResponse);

        mockMvc.perform(get("/api/v1/reports/types")
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(result))
                .andDo(document("reports/get-report-types",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                subsectionWithPath("report_types").description("신고 타입 목록"),
                                fieldWithPath("report_types[].id").type(JsonFieldType.NUMBER).description("신고 타입 아이디"),
                                fieldWithPath("report_types[].name").type(JsonFieldType.STRING).description("신고 타입 이름")
                        )
                ));
    }
}