package kr.co.yigil.notice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeCreateRequest;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeUpdateRequest;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class NoticeServiceImplTest {

    @InjectMocks
    private NoticeServiceImpl noticeService;

    @Mock
    private NoticeReader noticeReader;
    @Mock
    private NoticeStore noticeStore;

    @Mock
    private AdminReader adminReader;


    @DisplayName("createNotice 메서드가 잘 동작하는지")
    @Test
    void whenCreateNotice_thenShouldNotThrowAnError() {

        NoticeCreateRequest noticeCommand = new NoticeCreateRequest("title", "content");
        Admin mockAdmin = mock(Admin.class);
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);

        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockAuthentication.getName()).thenReturn("admin@example.com");
        when(adminReader.getAdminByEmail(any())).thenReturn(mockAdmin);

        noticeService.createNotice(noticeCommand);

        verify(noticeStore).save(any(Notice.class));
    }

    @DisplayName("getNotice 메서드가 잘 동작하는지")
    @Test
    void whenGetNotice_thenShouldReturnNoticeInfo() {
        Long noticeId = 1L;

        Admin admin = new Admin("hllov07@naver.com", "password", "nickname",
            List.of("ROLE_ADMIN"), mock(AttachFile.class));
        Notice notice = new Notice(admin, "title", "content");

        when(noticeReader.getNotice(noticeId)).thenReturn(notice);

        var response = noticeService.getNotice(noticeId);

        assertThat(response).isInstanceOf(NoticeInfo.NoticeDetail.class);
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getAuthorNickname()).isEqualTo(admin.getNickname());
        assertThat(response.getCreatedAt()).isEqualTo(notice.getCreatedAt());
        assertThat(response.getContent()).isEqualTo("content");
    }

    @DisplayName("getNoticeList 메서드가 잘 동작하는지")
    @Test
    void whenGetNoticeList_thenShouldReturnNoticeListInfo() {
        var pageRequest = mock(PageRequest.class);

        when(noticeReader.getNoticeList(any(PageRequest.class))).thenReturn(new PageImpl<>(
            List.of(new Notice(mock(Admin.class), "title", "content"))));

        var response = noticeService.getNoticeList(pageRequest);

        assertThat(response).isInstanceOf(NoticeListInfo.class);
        assertThat(response.getNoticeList()).hasSize(1);
    }

    @DisplayName("updateNotice 메서드가 잘 동작하는지")
    @Test
    void whenUpdateNotice_thenShouldNotThrowAnError() {
        Long noticeId = 1L;
        NoticeUpdateRequest noticeCommand = new NoticeUpdateRequest("title", "content");

        when(noticeReader.getNotice(noticeId)).thenReturn(
            new Notice(mock(Admin.class), "title", "content"));

        noticeService.updateNotice(noticeId, noticeCommand);

        verify(noticeReader).getNotice(noticeId);
    }

    @DisplayName("deleteNotice 메서드가 잘 동작하는지")
    @Test
    void whenDeleteNotice_thenShouldNotThrowAnError() {
        Long noticeId = 1L;

        noticeService.deleteNotice(noticeId);

        verify(noticeStore).delete(noticeId);
    }
}