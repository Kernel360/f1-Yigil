package kr.co.yigil.notice.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.notice.domain.Notice;
import kr.co.yigil.notice.domain.NoticeReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeReaderImpl implements NoticeReader {
    private final NoticeRepository noticeRepository;

    @Override
    public Notice getNotice(Long noticeId) {
        return noticeRepository.findById(noticeId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOTICE_NOT_FOUND)
        );
    }

    @Override
    public Slice<Notice> getNoticeList(PageRequest pageRequest) {
        return noticeRepository.findAll(pageRequest);
    }
}
