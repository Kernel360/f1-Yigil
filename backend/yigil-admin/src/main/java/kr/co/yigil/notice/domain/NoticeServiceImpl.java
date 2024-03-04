package kr.co.yigil.notice.domain;

import kr.co.yigil.notice.domain.NoticeCommand.NoticeCreateRequest;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeUpdateRequest;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeReader noticeReader;
    private final NoticeStore noticeStore;

    @Override
    @Transactional
    public void createNotice(NoticeCreateRequest noticeCommand) {
        var notice = noticeCommand.toEntity();
        noticeStore.save(notice);
    }

    @Override
    @Transactional(readOnly = true)
    public NoticeInfo.NoticeDetail getNotice(Long noticeId) {
        var notice = noticeReader.getNotice(noticeId);
        return new NoticeInfo.NoticeDetail(notice);
    }

    @Override
    @Transactional(readOnly = true)
    public NoticeListInfo getNoticeList(PageRequest pageRequest) {
        var noticeSlice = noticeReader.getNoticeList(pageRequest);
        return new NoticeListInfo(noticeSlice);
    }

    @Override
    @Transactional
    public void updateNotice(Long noticeId, NoticeUpdateRequest noticeCommand) {
        var notice = noticeReader.getNotice(noticeId);
        notice.updateNotice(noticeCommand.toEntity());
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId) {
        noticeStore.delete(noticeId);
    }
}
