package kr.co.yigil.notice.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.notice.application.NoticeFacade;
import kr.co.yigil.notice.domain.NoticeCommand;
import kr.co.yigil.notice.domain.NoticeInfo;
import kr.co.yigil.notice.domain.NoticeInfo.NoticeListInfo;
import kr.co.yigil.notice.interfaces.dto.NoticeDto;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeCreateRequest;
import kr.co.yigil.notice.interfaces.dto.mapper.NoticeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoticeApiController.class)
class NoticeApiControllerTest {

    @MockBean
    private NoticeFacade noticeFacade;
    @MockBean
    private NoticeMapper noticeMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .build();
    }

    @DisplayName("공지사항 목록 조회가 성공하면 OK 상태를 반환한다.")
    @Test
    void whenGetNoticeList_thenShouldReturnOKstatus() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 5);
        when(noticeFacade.getNoticeList(pageRequest)).thenReturn(mock(NoticeListInfo.class));
        when(noticeMapper.toDto(mock(NoticeListInfo.class))).thenReturn(mock(
            NoticeDto.NoticeListResponse.class));

        mockMvc.perform(get("/api/v1/notices"))
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항이 잘 생성되면 OK 상태를 반환한다.")
    @Test
    void whenCreateNotice_thenShouldReturnOk() throws Exception {

        NoticeCommand.NoticeCreateRequest command = mock(NoticeCommand.NoticeCreateRequest.class);

        when(noticeMapper.toCommand(any(NoticeCreateRequest.class))).thenReturn(command);
        doNothing().when(noticeFacade).createNotice(command);

        mockMvc.perform(post("/api/v1/notices"))
            .andExpect(status().isOk());
        verify(noticeFacade).createNotice(command);
    }

    @DisplayName("공지사항이 잘 조회되면 OK 상태를 반환한다.")
    @Test
    void readNotice() throws Exception {
        Long noticeId = 1L;

        NoticeDto.NoticeDetailResponse response = mock(NoticeDto.NoticeDetailResponse.class);
        when(noticeFacade.readNotice(anyLong())).thenReturn(mock(NoticeInfo.NoticeDetail.class));
        when(noticeMapper.toDto(any(NoticeInfo.NoticeDetail.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/notices/{noticeId}", noticeId))
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항이 잘 수정되면 OK 상태를 반환한다.")
    @Test
    void updateNotice() throws Exception {
        NoticeDto.NoticeUpdateRequest request = mock(NoticeDto.NoticeUpdateRequest.class);
        NoticeCommand.NoticeUpdateRequest command = mock(NoticeCommand.NoticeUpdateRequest.class);
        Long noticeId = 1L;

        when(noticeMapper.toCommand(request)).thenReturn(command);
        doNothing().when(noticeFacade).updateNotice(noticeId, command);

        mockMvc.perform(post("/api/v1/notices/{noticeId}/update", noticeId))
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항이 잘 삭제되면 OK 상태를 반환한다.")
    @Test
    void deleteNotice() throws Exception {
        Long noticeId = 1L;

        doNothing().when(noticeFacade).deleteNotice(noticeId);

        mockMvc.perform(post("/api/v1/notices/{noticeId}", noticeId))
            .andExpect(status().isOk());
    }
}