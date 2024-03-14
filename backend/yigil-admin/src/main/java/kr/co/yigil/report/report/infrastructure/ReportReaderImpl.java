package kr.co.yigil.report.report.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.report.report.domain.Report;
import kr.co.yigil.report.report.domain.ReportReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportReaderImpl implements ReportReader {

    private final ReportRepository reportRepository;

    @Override
    public Page<Report> getReportsByType(Long typeId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reportRepository.findAllByReportTypeId(typeId, pageRequest);
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new BadRequestException(ExceptionCode.INVALID_REQUEST)
        );
    }

    @Override
    public Page<Report> getAllReports(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reportRepository.findAll(pageRequest);
    }
}
