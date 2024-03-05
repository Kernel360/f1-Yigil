package kr.co.yigil.notice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface NoticeReader {
    Notice getNotice(Long noticeId);
    Page<Notice> getNoticeList(PageRequest pageRequest);
}
