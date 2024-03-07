package kr.co.yigil.notification.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.notification.application.NotificationFacade;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.interfaces.dto.mapper.NotificationMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationFacade notificationFacade;
    private final NotificationMapper notificationMapper;

    @GetMapping(path = "/api/v1/notifications/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @MemberOnly
    public Flux<ServerSentEvent<Notification>> streamNotifications(@Auth Accessor accessor) {
        return notificationFacade.getNotificationStream(accessor.getMemberId());
    }

    @GetMapping("/api/v1/notifications")
    @MemberOnly
    public ResponseEntity<NotificationsResponse> getNotifications(
        @Auth Accessor accessor,
        @PageableDefault(size = 5, page = 1) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
            pageable.getPageSize(),
            Sort.by(direction, sortBy.getValue()));
        Slice<Notification> notificationSlice = notificationFacade.getNotificationSlice(
            accessor.getMemberId(), pageRequest);
        NotificationsResponse response = notificationMapper.notificationSliceToNotificationsResponse(
            notificationSlice);
        return ResponseEntity.ok().body(response);
    }

    // todo : add notification read api
}
