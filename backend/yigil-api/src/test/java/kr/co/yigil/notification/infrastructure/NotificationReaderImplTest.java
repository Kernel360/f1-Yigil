package kr.co.yigil.notification.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import io.lettuce.core.protocol.DemandAware.Sink;
import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.test.StepVerifier;

public class NotificationReaderImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationReaderImpl notificationReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("getNotificationSlice 메서드가 올바른 Slice를 반환하는지")
    @Test
    void whenGetNotificationSlice_thenReturnsCorrectSlice() {
        Long memberId = 1L;
        List<Notification> notifications = new ArrayList<>();
        Slice<Notification> expectedSlice = new SliceImpl<>(notifications);

        when(notificationRepository.findAllByMemberId(memberId, Pageable.unpaged())).thenReturn(
                expectedSlice);

        Slice<Notification> actualSlice = notificationReader.getNotificationSlice(memberId,
                Pageable.unpaged());

        assertEquals(expectedSlice, actualSlice);
    }

}