package kr.co.yigil.member.interfaces.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.follow.dto.response.FollowerFindDto;
import kr.co.yigil.follow.dto.response.FollowingFindDto;
import kr.co.yigil.member.application.MemberFacade;
import kr.co.yigil.member.interfaces.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.interfaces.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.interfaces.dto.response.MemberInfoResponse;
import kr.co.yigil.member.interfaces.dto.response.MemberUpdateResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseFindDto;
import kr.co.yigil.travel.interfaces.dto.response.SpotFindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @GetMapping
    @MemberOnly
    public ResponseEntity<MemberInfoResponse> getMyInfo(@Auth final Accessor accessor) {
        final MemberInfoResponse response = memberFacade.getMemberInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/courses")
    @MemberOnly
    public ResponseEntity<Slice<CourseFindDto>> getMyCourseInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<CourseFindDto> response = memberFacade.getMemberCourseInfo(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/spots")
    @MemberOnly
    public ResponseEntity<Slice<SpotFindDto>> getMySpotInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<SpotFindDto> spotListResponse = memberFacade.getMemberSpotInfo(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(spotListResponse);
    }

    @GetMapping("/followers")
    @MemberOnly
    public ResponseEntity<Slice<FollowingFindDto>> getMyFollowerInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<FollowingFindDto> followerListResponse = memberFacade.getFollowingList(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(followerListResponse);
    }

    @GetMapping("/followings")
    @MemberOnly
    public ResponseEntity<Slice<FollowerFindDto>> getMyFollowingInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<FollowerFindDto> followingListResponse = memberFacade.getFollowerList(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(followingListResponse);
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<MemberUpdateResponse> updateMyInfo(@Auth final Accessor accessor, @ModelAttribute
    MemberUpdateRequest request) {
        final MemberUpdateResponse response = memberFacade.updateMemberInfo(accessor.getMemberId(), request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    @MemberOnly
    public ResponseEntity<MemberDeleteResponse> withdraw(HttpServletRequest request, @Auth final Accessor accessor) {
        final MemberDeleteResponse response = memberFacade.withdraw(accessor.getMemberId());
        request.getSession().invalidate();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{memberId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable("memberId") final Long memberId) {
        MemberInfoResponse response = memberFacade.getMemberInfo(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followers/{memberId}")
    public ResponseEntity<Slice<FollowerFindDto>> getMemberFollowerList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Slice<FollowerFindDto> response = memberFacade.getFollowerList(memberId, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followings/{memberId}")
    public ResponseEntity<Slice<FollowingFindDto>> getMemberFollowingList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Slice<FollowingFindDto> response = memberFacade.getFollowingList(memberId, pageRequest);
        return ResponseEntity.ok(response);
    }
}
