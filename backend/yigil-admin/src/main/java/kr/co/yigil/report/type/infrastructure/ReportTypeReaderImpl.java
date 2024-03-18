package kr.co.yigil.report.type.infrastructure;

import java.util.List;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportTypeReaderImpl implements ReportTypeReader {
    private final ReportTypeRepository reportTypeRepository;

    @Override
    public List<ReportType> getCategories() {
        return reportTypeRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return reportTypeRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long categoryId) {
        return reportTypeRepository.existsById(categoryId);
    }
}
