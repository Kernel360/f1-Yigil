package kr.co.yigil.report.type.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.report.type.domain.ReportType;
import kr.co.yigil.report.type.domain.ReportTypeReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportTypeReaderImpl implements ReportTypeReader {
    private final ReportTypeRepository reportTypeRepository;


    @Override
    public List<ReportType> getReportTypes() {
        return reportTypeRepository.findAll();
    }

    @Override
    public ReportType getReportType(Long reportTypeId) {
        return reportTypeRepository.findById(reportTypeId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_REPORT_TYPE_ID)
        );
    }
}
