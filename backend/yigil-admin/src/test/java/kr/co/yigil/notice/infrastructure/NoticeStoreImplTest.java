package kr.co.yigil.notice.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.notice.domain.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoticeStoreImplTest {

    @InjectMocks
    private NoticeStoreImpl noticeStore;

    @Mock
    private NoticeRepository noticeRepository;

    @DisplayName("save 메서드가 잘 동작하는지")
    @Test
    void save() {
        when(noticeRepository.save(any(Notice.class))).thenReturn(mock(Notice.class));
        var result = noticeStore.save(mock(Notice.class));
        assertThat(result).isInstanceOf(Notice.class);
    }

    @DisplayName("delete 메서드가 잘 동작하는지")
    @Test
    void delete() {
        noticeStore.delete(1L);
        verify(noticeRepository).deleteById(1L);
    }
}