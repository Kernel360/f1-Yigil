package kr.co.yigil.report.type.domain;

import java.util.List;
import kr.co.yigil.report.type.domain.ReportTypeCommand.CreateReportType;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportTypeServiceImpl implements ReportTypeService {

    private final ReportTypeReader reportTypeReader;
    private final ReportTypeStore reportTypeStore;

    @Override
    @Transactional
    public List<ReportType> getCategories() {
        return reportTypeReader.getCategories();
    }

    @Transactional
    @Override
    public void createReportType(CreateReportType command) {
        String name = command.getName();
        if (reportTypeReader.existsByName(name)) {
            throw new BadRequestException(ExceptionCode.REPORT_TYPE_DUPLICATED);
        }
        reportTypeStore.createCategory(command);
    }

    @Override
    public void deleteReportType(Long typeId) {
        if(!reportTypeReader.existsById(typeId)){
            throw new BadRequestException(ExceptionCode.REPORT_TYPE_NOT_FOUND);
        }
        reportTypeStore.deleteCategory(typeId);
    }
}
