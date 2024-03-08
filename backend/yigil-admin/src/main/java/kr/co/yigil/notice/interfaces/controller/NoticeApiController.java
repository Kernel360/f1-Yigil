package kr.co.yigil.notice.interfaces.controller;

import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.notice.application.NoticeFacade;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeCreateRequest;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeCreateResponse;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeDeleteResponse;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeDetailResponse;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeListResponse;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeUpdateRequest;
import kr.co.yigil.notice.interfaces.dto.NoticeDto.NoticeUpdateResponse;
import kr.co.yigil.notice.interfaces.dto.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeApiController {

    private final NoticeFacade noticeFacade;
    private final NoticeMapper noticeMapper;

    @GetMapping
    public ResponseEntity<NoticeListResponse> geNoticeList(
        @PageableDefault(size = 5, page = 1) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
            pageable.getPageSize(),
            Sort.by(direction, sortBy.getValue()));
        var notice = noticeFacade.getNoticeList(pageRequest);
        var response = noticeMapper.toDto(notice);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<NoticeCreateResponse> createNotice(
        @RequestBody NoticeCreateRequest request
    ){
        var noticeCommand = noticeMapper.toCommand(request);
        noticeFacade.createNotice(noticeCommand);
        return ResponseEntity.ok().body(new NoticeCreateResponse("공지사항 등록 완료"));
    }

    @PostMapping("/{noticeId}")
    public ResponseEntity<NoticeDetailResponse> readNotice(
        @PathVariable("noticeId") Long noticeId
    ){
        var noticeInfo = noticeFacade.readNotice(noticeId);
        var response = noticeMapper.toDto(noticeInfo);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{noticeId}/update")
    public ResponseEntity<NoticeUpdateResponse> updateNotice(
        @PathVariable("noticeId") Long noticeId,
        @ModelAttribute NoticeUpdateRequest request
    ){
        // 관리자 권한 필요
        var noticeCommand = noticeMapper.toCommand(request);
        noticeFacade.updateNotice(noticeId, noticeCommand);
        return ResponseEntity.ok().body(new NoticeUpdateResponse("공지사항 수정 완료"));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<NoticeDeleteResponse> deleteNotice(
        @PathVariable("noticeId") Long noticeId
    ){
        noticeFacade.deleteNotice(noticeId);
        return ResponseEntity.ok().body(new NoticeDeleteResponse("공지사항 삭제 완료"));
    }
}