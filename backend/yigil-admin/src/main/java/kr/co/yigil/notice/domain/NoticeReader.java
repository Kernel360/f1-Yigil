package kr.co.yigil.notice.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

public interface NoticeReader {
    Notice getNotice(Long noticeId);
    Slice<Notice> getNoticeList(PageRequest pageRequest);
}
