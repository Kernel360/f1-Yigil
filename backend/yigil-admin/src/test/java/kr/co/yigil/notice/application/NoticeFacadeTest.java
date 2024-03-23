package kr.co.yigil.notice.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.notice.domain.NoticeCommand.NoticeCreateRequest;
import kr.co.yigil.notice.domain.NoticeCommand.NoticeUpdateRequest;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeDetail;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import kr.co.yigil.notice.domain.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;


@ExtendWith(MockitoExtension.class)
class NoticeFacadeTest {

    @InjectMocks
    private NoticeFacade noticeFacade;
    @Mock
    private NoticeService noticeService;

    @DisplayName("공지사항 목록 조회가 성공하면 ")
    @Test
    void whenGetNoticeList_thenShouldReturnNoticeListInfo() {
        PageRequest pageRequest = mock(PageRequest.class);
        when(noticeService.getNoticeList(pageRequest)).thenReturn(mock(NoticeListInfo.class));
        var result = noticeFacade.getNoticeList(pageRequest);
        assertThat(result).isNotNull();
    }

    @DisplayName("공지사항을 생성하면 에러를 발생시키지 않는다")
    @Test
    void whenCreateNotice_thenShouldNotThrowAnError() {
        noticeFacade.createNotice(mock(NoticeCreateRequest.class));
        verify(noticeService).createNotice(any());
    }

    @DisplayName("공지사항을 조회하면 NoticeDetail 객체를 반환한다")
    @Test
    void whenCreadNotice_thenShouldReturnNoticeDetail() {

        when(noticeService.getNotice(any())).thenReturn(mock(NoticeDetail.class));
        var result = noticeFacade.readNotice(1L);
        assertThat(result).isNotNull();
    }

    @DisplayName("공지사항을 수정하면 에러를 발생시키지 않는다")
    @Test
    void whenUpdateNotice_thenShouldNotThrowAnError() {
        noticeFacade.updateNotice(1L, mock(NoticeUpdateRequest.class));
        verify(noticeService).updateNotice(any(), any());
    }

    @DisplayName("공지사항을 삭제하면 에러를 발생시키지 않는다")
    @Test
    void whenDeleteNotice_thenShouldNotThrowAnError() {
        noticeFacade.deleteNotice(1L);
        verify(noticeService).deleteNotice(any());
    }
}