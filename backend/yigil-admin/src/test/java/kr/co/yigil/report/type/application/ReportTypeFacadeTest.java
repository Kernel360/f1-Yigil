package kr.co.yigil.report.type.application;

import static org.mockito.Mockito.verify;

import kr.co.yigil.report.type.domain.ReportTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportTypeFacadeTest {

    @InjectMocks
    private ReportTypeFacade reportTypeFacade;

    @Mock
    private ReportTypeService reportTypeService;


    @DisplayName("카테고리 목록 조회시 reportTypeService.getCategories() 가 잘 호출되는지")
    @Test
    void whenGetCategories_verifyCallGetcategories() {
        reportTypeFacade.getCategories();
        verify(reportTypeService).getCategories();
    }


    @DisplayName("카테고리 생성 시 reportTypeService.createReportType() 가 잘 호출되는지")
    @Test
    void whenCreateReportType_thenVerifyCallDeleteReportType() {
        Long categoryId = 1L;

        reportTypeFacade.deleteReportType(categoryId);
        verify(reportTypeService).deleteReportType(categoryId);
    }


    @DisplayName("카테고리 삭제 시 reportTypeService.deleteReportType() 가 잘 호출되는지")
    @Test
    void whenDeleteReportType_thenShouldCallDeleteReportType() {
        reportTypeFacade.deleteReportType(1L);
        verify(reportTypeService).deleteReportType(1L);
    }
}