package kr.co.yigil.notification.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.notification.application.NotificationFacade;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.interfaces.controller.NotificationApiController;
import kr.co.yigil.notification.interfaces.dto.NotificationInfoDto;
import kr.co.yigil.notification.interfaces.dto.mapper.NotificationMapper;
import kr.co.yigil.notification.interfaces.dto.response.NotificationsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(NotificationApiController.class)
@AutoConfigureRestDocs
public class NotificationApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private NotificationFacade notificationFacade;

    @MockBean
    private NotificationMapper notificationMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("Notificationstream이 올바르게 동작하는지 테스트")
    @Test
    void whenStreamNotification_thenReturns200() throws Exception {
        Member member = new Member(1L, "email", "12345678", "nickname", "image.jpg", SocialLoginType.KAKAO);
        Notification notification = new Notification(member, "새로운 알림입니다.");
        ServerSentEvent<Notification> sse = ServerSentEvent.builder(notification).id("1").event("test event").build();

        when(notificationFacade.getNotificationStream(anyLong())).thenReturn(Flux.just(sse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications/stream"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_EVENT_STREAM_VALUE))
                .andDo(document(
                        "notifications/stream-notification",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseBody()
                ));

        verify(notificationFacade).getNotificationStream(anyLong());
    }

    @DisplayName("GetNotifications가 올바르게 동작하는지 테스트")
    @Test
    void whenGetNotifications_thenReturns200AndNotificationsResponse() throws Exception {
        Member member = new Member(1L, "email", "12345678", "nickname", "image.jpg",
                SocialLoginType.KAKAO);
        Notification notification = new Notification(member, "새로운 알림입니다.");
        when(notificationFacade.getNotificationSlice(anyLong(), any(PageRequest.class))).thenReturn(new SliceImpl<>(List.of(notification)));
        NotificationInfoDto notificationInfoDto = new NotificationInfoDto("message", "createDate");
        when(notificationMapper.notificationSliceToNotificationsResponse(new SliceImpl<>(List.of(notification)))).thenReturn(new NotificationsResponse(List.of(notificationInfoDto), false));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications"))
                .andExpect(status().isOk())
                .andDo(document(
                        "notifications/get-notifications",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("page").description("현재 페이지").optional(),
                                parameterWithName("size").description("페이지 크기").optional(),
                                parameterWithName("sortBy").description("정렬 옵션").optional(),
                                parameterWithName("sortOrder").description("정렬 순서").optional()
                        ),
                        responseFields(
                                fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지가 있는지 여부"),
                                subsectionWithPath("notifications").description("notification의 정보"),
                                fieldWithPath("notifications[].message").description("Notification의 메시지"),
                                fieldWithPath("notifications[].create_date").type(JsonFieldType.STRING).description("Notification의 생성일시")
                        )
                ));

        verify(notificationFacade).getNotificationSlice(anyLong(), any(PageRequest.class));
        verify(notificationMapper).notificationSliceToNotificationsResponse(new SliceImpl<>(List.of(notification)));

    }
}