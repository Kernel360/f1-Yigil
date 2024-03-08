package kr.co.yigil.notice.domain;

import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeCreateRequest;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeUpdateRequest;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeReader noticeReader;
    private final NoticeStore noticeStore;
    private final AdminReader adminReader;

    @Override
    @Transactional
    public void createNotice(NoticeCreateRequest noticeCommand) {
        Authentication authentication = SecurityContextHolder
            .getContext()
            .getAuthentication();
        var admin = adminReader.getAdminByEmail(authentication.getName());
        var notice = noticeCommand.toEntity(admin);
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
        notice.updateNotice(noticeCommand.getTitle(), noticeCommand.getContent());
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId) {
        noticeStore.delete(noticeId);
    }
}
