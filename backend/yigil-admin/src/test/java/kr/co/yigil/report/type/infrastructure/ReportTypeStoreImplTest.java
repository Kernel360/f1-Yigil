package kr.co.yigil.report.type.infrastructure;

import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportTypeStoreImplTest {
    @InjectMocks
    private ReportTypeStoreImpl reportTypeStore;

    @Mock
    private ReportTypeRepository reportTypeRepository;

    @DisplayName("createCategory 저장을 잘 하는지")
    @Test
    void whenCreateCategory_thenShouldCallSave() {
        // given
        ReportTypeCommand.CreateReportType command = ReportTypeCommand.CreateReportType.builder()
            .name("Q&A")
            .description("질문과 답변")
            .build();

        // when
        reportTypeStore.createCategory(command);
        // then
        verify(reportTypeRepository).save(any(ReportType.class));
    }

    @DisplayName("deleteCategory 삭제를 잘 하는지")
    @Test
    void deleteCategory() {
        // given
        Long categoryId = 1L;
        // when
        reportTypeStore.deleteCategory(categoryId);
        // then
        verify(reportTypeRepository).deleteById(categoryId);
    }
}