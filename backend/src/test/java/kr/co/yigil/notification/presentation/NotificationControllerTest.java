package kr.co.yigil.notification.presentation;

import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Notification stream이 올바르게 반환되는지 테스트")
    @Test
    void testStreamNotifications() throws Exception {
        Member member = new Member(1L, "email", "12345678", "nickname", "image.jpg", SocialLoginType.KAKAO);
        Notification notification = new Notification(member, "새로운 알림입니다.");
        Flux<ServerSentEvent<Notification>> notificationFlux = Flux.just(ServerSentEvent.builder(notification).build());

        Mockito.when(notificationService.getNotificationStream(member.getId())).thenReturn(notificationFlux);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications/stream"))
                .andExpect(MockMvcResultMatchers.status().isOk());
   }
}
