package kr.co.yigil.notification.infrastructure;

import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


public class NotificationStoreImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationStoreImpl notificationStore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenStore_thenSaveIsCalled() {
        Long memberId = 1L;
        Member member = new Member(memberId, "kiit0901@gmail.com", "123456", "stone", "profile.jpg", SocialLoginType.KAKAO);
        Member sender = mock(Member.class);
        Notification notification = new Notification(member, sender,"Notification content");

        notificationStore.store(notification);

        verify(notificationRepository, times(1)).save(notification);
    }
}