package kr.co.yigil.notice.domain;

import kr.co.yigil.notice.domain.NoticeCommand.NoticeCreateRequest;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeUpdateRequest;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeDetail;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import org.springframework.data.domain.PageRequest;

public interface NoticeService {

    void createNotice(NoticeCreateRequest noticeCommand);

    NoticeDetail getNotice(Long noticeId);

    NoticeListInfo getNoticeList(PageRequest pageRequest);

    void updateNotice(Long noticeId, NoticeUpdateRequest noticeCommand);

    void deleteNotice(Long noticeId);
}
