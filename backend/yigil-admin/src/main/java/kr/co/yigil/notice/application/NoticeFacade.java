package kr.co.yigil.notice.application;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeCreateRequest;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeUpdateRequest;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeDetail;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import kr.co.yigil.notice.domain.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeFacade {

    private final NoticeService noticeService;
    private final AdminService adminService;


    public NoticeListInfo getNoticeList(PageRequest pageRequest) {
        return noticeService.getNoticeList(pageRequest);
    }

    public void createNotice(NoticeCreateRequest noticeCommand) {
        noticeService.createNotice(noticeCommand);
    }

    public NoticeDetail readNotice(Long noticeId) {
        return noticeService.getNotice(noticeId);
    }

    public void updateNotice(Long noticeId, NoticeUpdateRequest noticeCommand) {
        noticeService.updateNotice(noticeId, noticeCommand);
    }

    public void deleteNotice(Long noticeId) {
        noticeService.deleteNotice(noticeId);
    }

    private String getAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
