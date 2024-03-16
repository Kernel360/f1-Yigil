package kr.co.yigil.report.report.infrastructure;


import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.report.report.domain.Report;
import kr.co.yigil.report.report.domain.ReportReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportReaderImpl implements ReportReader {
    private final ReportRepository reportRepository;

    @Override
    public Report getMemberReport(Long memberId, Long id) {
        return reportRepository.findByIdAndReporterId(id, memberId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_REPORT_ID));
    }

    @Override
    public Page<Report> getMemberReportList(Long memberId, Long typeId, String keyword, Pageable pageable) {
        return reportRepository.findAllByMemberIdByReportTypeIdAndContentContains(memberId, typeId, keyword, pageable);
    }

    @Override
    public Report getMyReport(Long memberId, Long id) {
        return reportRepository.findByIdAndReporterId(id, memberId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.INVALID_AUTHORITY));
    }
}
