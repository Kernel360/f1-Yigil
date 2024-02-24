package kr.co.yigil.member.interfaces.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.interfaces.dto.MemberDto;
import kr.co.yigil.member.interfaces.dto.mapper.MemberDtoMapper;
import kr.co.yigil.member.interfaces.dto.response.MemberDeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final MemberFacade memberFacade;
    private final MemberDtoMapper memberDtoMapper;

    @GetMapping
    @MemberOnly
    public ResponseEntity<MemberDto.Main> getMyInfo(@Auth final Accessor accessor) {
        var memberInfo = memberFacade.getMemberInfo(accessor.getMemberId());
        var response = memberDtoMapper.of(memberInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/courses")
    @MemberOnly
    public ResponseEntity<MemberDto.MemberCourseResponse> getMyCourseInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder,
        @RequestParam(name = "selected", defaultValue = "false", required = false) String selected
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));

        final var memberCourseInfo = memberFacade.getMemberCourseInfo(
            accessor.getMemberId(), pageRequest, selected);
        var memberCourseResponse = memberDtoMapper.of(memberCourseInfo);
        return ResponseEntity.ok().body(memberCourseResponse);
    }

    @GetMapping("/spots")
    @MemberOnly
    public ResponseEntity<MemberDto.MemberSpotResponse> getMySpotInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder,
        @RequestParam(name = "selected", defaultValue = "false", required = false) String selected
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final MemberInfo.MemberSpotResponse spotListResponse = memberFacade.getMemberSpotInfo(
            accessor.getMemberId(), pageRequest, selected);
        var response = memberDtoMapper.of(spotListResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/followers")
    @MemberOnly
    public ResponseEntity<MemberDto.FollowerResponse> getMyFollowerList(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        MemberInfo.FollowerResponse followerListResponse = memberFacade.getFollowerList(
            accessor.getMemberId(), pageRequest);
        var response = memberDtoMapper.of(followerListResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/followings")
    @MemberOnly
    public ResponseEntity<MemberDto.FollowingResponse> getMyFollowingList(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final MemberInfo.FollowingResponse followingListResponse = memberFacade.getFollowingList(
            accessor.getMemberId(), pageRequest);
        MemberDto.FollowingResponse response = memberDtoMapper.of(followingListResponse);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<MemberDto.MemberUpdateResponse> updateMyInfo(
        @Auth final Accessor accessor,
        @ModelAttribute MemberDto.MemberUpdateRequest request
    ) {
        var memberCommand = memberDtoMapper.of(request);
        var message = memberFacade.updateMemberInfo(accessor.getMemberId(),
            memberCommand);
        var response = memberDtoMapper.of(message);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    @MemberOnly
    public ResponseEntity<MemberDeleteResponse> withdraw(HttpServletRequest request,
        @Auth final Accessor accessor) {
        final MemberDeleteResponse response = memberFacade.withdraw(accessor.getMemberId());
        request.getSession().invalidate();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto.Main> getMemberInfo(
        @PathVariable("memberId") final Long memberId) {
        var memberInfo = memberFacade.getMemberInfo(memberId);
        var response = memberDtoMapper.of(memberInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followers/{memberId}")
    public ResponseEntity<MemberDto.FollowerResponse> getMemberFollowerList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        MemberInfo.FollowerResponse followerList = memberFacade.getFollowerList(memberId, pageRequest);
        var response = memberDtoMapper.of(followerList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followings/{memberId}")
    public ResponseEntity<MemberDto.FollowingResponse> getMemberFollowingList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        MemberInfo.FollowingResponse followingList = memberFacade.getFollowingList(memberId, pageRequest);
        var response = memberDtoMapper.of(followingList);
        return ResponseEntity.ok(response);
    }

}
