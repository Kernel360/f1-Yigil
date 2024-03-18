package kr.co.yigil.notification.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.notification.application.NotificationFacade;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.interfaces.dto.NotificationInfoDto;
import kr.co.yigil.notification.interfaces.dto.mapper.NotificationMapper;
import kr.co.yigil.notification.interfaces.dto.request.NotificationReadRequest;
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

import java.util.List;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Member member = new Member(1L, "email", "12345678", "nickname", "image.jpg",
                SocialLoginType.KAKAO);
        Notification notification = new Notification(member, "새로운 알림입니다.");
        ServerSentEvent<Notification> sse = ServerSentEvent.builder(notification).id("1")
                .event("test event").build();

        when(notificationFacade.getNotificationStream(anyLong())).thenReturn(Flux.just(sse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications/stream"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andExpect(content().contentType(MediaType.TEXT_EVENT_STREAM_VALUE))
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
        Long notificationId = 1L;
        Notification notification = new Notification(member, "새로운 알림입니다.");
        when(notificationFacade.getNotificationSlice(anyLong(), any(PageRequest.class))).thenReturn(
                new SliceImpl<>(List.of(notification)));
        NotificationInfoDto notificationInfoDto1 = new NotificationInfoDto(notificationId,
                "message", "createDate", true);
        NotificationInfoDto notificationInfoDto2 = new NotificationInfoDto(2L, "message",
                "createDate", false);
        when(notificationMapper.notificationSliceToNotificationsResponse(
                any())).thenReturn(
                new NotificationsResponse(List.of(notificationInfoDto1), false));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications"))
                .andExpect(status().isOk())
                .andDo(document(
                        "notifications/get-notifications",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("page").description("현재 페이지").optional(),
                                parameterWithName("size").description("페이지 크기").optional()
                        ),
                        responseFields(
                                fieldWithPath("has_next").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지가 있는지 여부"),
                                subsectionWithPath("notifications").description("notification의 정보"),
                                fieldWithPath("notifications[].message").description("Notification의 메시지"),
                                fieldWithPath("notifications[].create_date").type(JsonFieldType.STRING)
                                        .description("Notification의 생성일시")
                        )
                ));

        verify(notificationFacade).getNotificationSlice(anyLong(), any(PageRequest.class));
        verify(notificationMapper).notificationSliceToNotificationsResponse(
                new SliceImpl<>(List.of(notification)));
    }

    @DisplayName("ReadNotification이 올바르게 동작하는지 테스트")
    @Test
    void whenReadNotification_thenReturns200AndNotificationReadResponse() throws Exception {

        Long notificationId = 1L;
        List<Long> ids = List.of(notificationId);
        NotificationReadRequest request = new NotificationReadRequest();
        request.setIds(ids);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String requestJson = objectMapper.writeValueAsString(request);


        mockMvc.perform(post("/api/v1/notifications/read")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(document(
                        "notifications/read-notification",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("ids").description("읽을 Notification의 id")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("읽은 Notification의 메시지")
                        )
                ));

        verify(notificationFacade).readNotification(anyLong(), any());
    }
}