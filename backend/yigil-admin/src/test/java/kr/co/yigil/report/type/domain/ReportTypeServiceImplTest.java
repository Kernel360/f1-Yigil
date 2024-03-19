package kr.co.yigil.report.type.domain;

import kr.co.yigil.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportTypeServiceImplTest {

    @InjectMocks
    private ReportTypeServiceImpl reportTypeService;

    @Mock
    private ReportTypeStore reportTypeStore;
    @Mock
    private ReportTypeReader reportTypeReader;


    @DisplayName("신고 유형 목록 조회 요청이 오면 카테고리 목록을 반환한다.")
    @Test
    void whenGetCategories_thenShouldReturnTypeList() {
        ReportType mockReport1 = mock(ReportType.class);
        ReportType mockReport2 = mock(ReportType.class);
        List<ReportType> mockReportList = List.of(mockReport1, mockReport2);
        when(reportTypeReader.getCategories()).thenReturn(mockReportList);

        var result = reportTypeService.getCategories();

        assertThat(result).isEqualTo(mockReportList);

    }

    @DisplayName("신고 유형 생성 요청이 오면 카테고리를 생성한다.")
    @Test
    void createReportType() {
        ReportTypeCommand.CreateReportType command = ReportTypeCommand.CreateReportType.builder()
            .name("Q&A")
            .description("질문과 답변")
            .build();
        when(reportTypeReader.existsByName(anyString())).thenReturn(false);
        reportTypeService.createReportType(command);
        verify(reportTypeStore).createCategory(command);
    }

    @DisplayName("신고 유형 생성 요청이 오면 카테고리를 생성한다.")
    @Test
    void givenExistName_whenCreateReportType_thenShouldThrowAnError() {
        ReportTypeCommand.CreateReportType command = ReportTypeCommand.CreateReportType.builder()
                .name("Q&A")
                .description("질문과 답변")
                .build();
        when(reportTypeReader.existsByName(anyString())).thenReturn(true);
        assertThrows(BadRequestException.class, () -> reportTypeService.createReportType(command));
    }

    @DisplayName("신고 유형 생성 요청이 오면 카테고리를 생성한다.")
    @Test
    void deleteReportType() {
        Long typeId = 1L;
        when(reportTypeReader.existsById(typeId)).thenReturn(true);
        reportTypeService.deleteReportType(typeId);
        verify(reportTypeStore).deleteCategory(typeId);
    }
}