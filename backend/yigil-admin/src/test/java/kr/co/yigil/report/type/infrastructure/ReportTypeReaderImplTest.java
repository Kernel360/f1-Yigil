package kr.co.yigil.report.type.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ReportTypeReaderImplTest {

    @InjectMocks
    private ReportTypeReaderImpl reportTypeReader;

    @Mock
    private ReportTypeRepository reportTypeRepository;

    @DisplayName("신고 유형 목록 조회 요청이 오면 카테고리 목록을 반환한다.")
    @Test
    void getCategories() {
        var result = reportTypeReader.getCategories();
        assertThat(result).isEqualTo(reportTypeRepository.findAll());
    }

    @DisplayName("신고 유형 이름으로 존재 여부를 확인한다.")
    @Test
    void existsByName() {
        boolean result = reportTypeReader.existsByName("Q&A");
        assertThat(result).isEqualTo(reportTypeRepository.existsByName("Q&A"));
    }

    @DisplayName("신고 유형 ID로 존재 여부를 확인한다.")
    @Test
    void existsById() {
        boolean result = reportTypeReader.existsById(1L);
        assertThat(result).isEqualTo(reportTypeRepository.existsById(1L));
    }
}