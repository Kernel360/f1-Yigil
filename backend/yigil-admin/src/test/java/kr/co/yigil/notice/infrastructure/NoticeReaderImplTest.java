package kr.co.yigil.notice.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.notice.domain.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@ExtendWith(MockitoExtension.class)
class NoticeReaderImplTest {
    @InjectMocks
    private NoticeReaderImpl noticeReader;
    @Mock
    private NoticeRepository noticeRepository;

    @DisplayName("getNotice 메서드가 Notice를 잘 반환하는지")
    @Test
    void whenGetNotice_thenShouldReturnNotice() {
        Long noticeId = 1L;

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(mock(Notice.class)));

        Notice result = noticeReader.getNotice(noticeId);
        assertThat(result).isInstanceOf(Notice.class);
    }

    @DisplayName("getNoticeList 메서드가 Slice<Notice>를 잘 반환하는지")
    @Test
    void whenGetNoticeList_thenShouldReturnNoticeSlice() {

        Notice notice1 = mock(Notice.class);
        Notice notice2 = mock(Notice.class);
        List<Notice> noticeList = List.of(notice1, notice2);
        PageRequest pageRequest = PageRequest.of(0, 10);
        var noticeSlice = new PageImpl<>(noticeList, PageRequest.of(0, 10), 2L);
        when(noticeRepository.findAll(any(PageRequest.class))).thenReturn(noticeSlice);
        Slice<Notice> result = noticeReader.getNoticeList(pageRequest);

        assertThat(result).isInstanceOf(Slice.class);

    }
}