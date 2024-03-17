package kr.co.yigil.report.type.infrastructure;

import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportTypeStoreImpl implements ReportTypeStore {
    private final ReportTypeRepository reportTypeRepository;

    @Override
    public void createCategory(CreateReportType command) {
        ReportType newCategory = command.toEntity();
        reportTypeRepository.save(newCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        reportTypeRepository.deleteById(categoryId);
    }
}
