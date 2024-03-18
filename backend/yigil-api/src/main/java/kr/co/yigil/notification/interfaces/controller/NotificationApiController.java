package kr.co.yigil.notification.interfaces.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.SSETimeoutException;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.MemberStatus;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.notification.application.NotificationFacade;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationSender;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.notification.interfaces.dto.mapper.NotificationMapper;
import kr.co.yigil.notification.interfaces.dto.request.NotificationReadRequest;
import kr.co.yigil.notification.interfaces.dto.response.NotificationReadResponse;
import kr.co.yigil.notification.interfaces.dto.response.NotificationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationFacade notificationFacade;
    private final NotificationMapper notificationMapper;

    @GetMapping(path = "/api/v1/notifications/stream")
    @MemberOnly
    public SseEmitter streamNotifications(@Auth Accessor accessor) {
        return notificationFacade.createEmitter(accessor.getMemberId());
    }


    @GetMapping("/api/v1/notifications")
    @MemberOnly
    public ResponseEntity<NotificationsResponse> getNotifications(
        @Auth Accessor accessor,
        @PageableDefault(size = 5, page = 1) Pageable pageable
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
            pageable.getPageSize(), Sort.by("createdAt").descending());
        Slice<Notification> notificationSlice = notificationFacade.getNotificationSlice(
            accessor.getMemberId(), pageRequest);
        NotificationsResponse response = notificationMapper.notificationSliceToNotificationsResponse(
            notificationSlice);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/notifications/read")
    @MemberOnly
    public ResponseEntity<NotificationReadResponse> readNotification(
        @Auth Accessor accessor,
        @RequestBody NotificationReadRequest request
    ) {

        notificationFacade.readNotification(accessor.getMemberId(), request.getIds());
        return ResponseEntity.ok()
            .body(new NotificationReadResponse("읽음 처리 하였습니다."));
    }
}
