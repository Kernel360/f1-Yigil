package kr.co.yigil.member.presentation;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.follow.dto.response.FollowerFindDto;
import kr.co.yigil.follow.dto.response.FollowingFindDto;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.dto.request.MemberUpdateRequest;
import kr.co.yigil.member.dto.response.MemberDeleteResponse;
import kr.co.yigil.member.dto.response.MemberInfoResponse;
import kr.co.yigil.member.dto.response.MemberUpdateResponse;
import kr.co.yigil.travel.dto.response.CourseFindDto;
import kr.co.yigil.travel.dto.response.SpotFindDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    @MemberOnly
    public ResponseEntity<MemberInfoResponse> getMyInfo(@Auth final Accessor accessor) {
        final MemberInfoResponse response = memberService.getMemberInfo(accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/v1/members/courses")
    @MemberOnly
    public ResponseEntity<Slice<CourseFindDto>> getMyCourseInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<CourseFindDto> response = memberService.getMemberCourseInfo(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/v1/members/spots")
    @MemberOnly
    public ResponseEntity<Slice<SpotFindDto>> getMySpotInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder
        ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<SpotFindDto> spotListResponse = memberService.getMemberSpotInfo(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(spotListResponse);
    }

    @DeleteMapping("/api/v1/members/")

    @GetMapping("/api/v1/members/followers")
    @MemberOnly
    public ResponseEntity<Slice<FollowingFindDto>> getMyFollowerInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<FollowingFindDto> follwerListResponse = memberService.getFollowingList(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(follwerListResponse);
    }

    @GetMapping("/api/v1/members/followings")
    @MemberOnly
    public ResponseEntity<Slice<FollowerFindDto>> getMyFollowingInfo(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final Slice<FollowerFindDto> follwingListResponse = memberService.getFollowerList(accessor.getMemberId(), pageRequest);
        return ResponseEntity.ok().body(follwingListResponse);
    }

    @PostMapping("/api/v1/members")
    @MemberOnly
    public ResponseEntity<MemberUpdateResponse> updateMyInfo(@Auth final Accessor accessor, @ModelAttribute
    MemberUpdateRequest request) {
        final MemberUpdateResponse response = memberService.updateMemberInfo(accessor.getMemberId(), request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/v1/members")
    @MemberOnly
    public ResponseEntity<MemberDeleteResponse> withdraw(HttpServletRequest request, @Auth final Accessor accessor) {
        final MemberDeleteResponse response = memberService.withdraw(accessor.getMemberId());
        request.getSession().invalidate();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/api/v1/members/{memberId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable("memberId") final Long memberId) {
        MemberInfoResponse response = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/members/followers/{memberId}")
    public ResponseEntity<Slice<FollowerFindDto>> getMemberFollowerList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
        ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Slice<FollowerFindDto> response = memberService.getFollowerList(memberId, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/members/followings/{memberId}")
    public ResponseEntity<Slice<FollowingFindDto>> getMemberFollowingList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
        ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Slice<FollowingFindDto> response = memberService.getFollowingList(memberId, pageRequest);
        return ResponseEntity.ok(response);
    }
}
