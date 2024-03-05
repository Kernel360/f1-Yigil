package kr.co.yigil.follow.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.follow.application.FollowFacade;
import kr.co.yigil.follow.domain.FollowInfo;
import kr.co.yigil.follow.dto.response.FollowResponse;
import kr.co.yigil.follow.dto.response.UnfollowResponse;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowersResponse;
import kr.co.yigil.follow.interfaces.dto.FollowDto.FollowingsResponse;
import kr.co.yigil.follow.interfaces.dto.FollowDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowApiController {
    private final FollowFacade followFacade;
    private final FollowDtoMapper followDtoMapper;
    @PostMapping("/follow/{member_id}")
    @MemberOnly
    public ResponseEntity<FollowResponse> follow(@Auth final Accessor accessor,
            @PathVariable("member_id") final Long memberId) {
        followFacade.follow(accessor.getMemberId(), memberId);
        return ResponseEntity.ok().body(new FollowResponse("팔로우 성공"));
    }

    @PostMapping("/unfollow/{member_id}")
    @MemberOnly
    public ResponseEntity<UnfollowResponse> unfollow(@Auth final Accessor accessor,
            @PathVariable("member_id") final Long memberId) {

        followFacade.unfollow(accessor.getMemberId(), memberId);
        return ResponseEntity.ok().body(new UnfollowResponse("언팔로우 성공"));
    }

    @GetMapping("/followers")
    @MemberOnly
    public ResponseEntity<FollowersResponse> getMyFollowerList(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5, page = 1) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        FollowInfo.FollowersResponse followerListResponse = followFacade.getFollowerList(
            accessor.getMemberId(), pageRequest);
        var response = followDtoMapper.of(followerListResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/followings")
    @MemberOnly
    public ResponseEntity<FollowingsResponse> getMyFollowingList(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final FollowInfo.FollowingsResponse followingListResponse = followFacade.getFollowingList(
            accessor.getMemberId(), pageRequest);
        FollowingsResponse response = followDtoMapper.of(followingListResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{memberId}/followers")
    public ResponseEntity<FollowersResponse> getMemberFollowerList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        FollowInfo.FollowersResponse followerList = followFacade.getFollowerList(memberId, pageRequest);
        var response = followDtoMapper.of(followerList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}/followings")
    public ResponseEntity<FollowingsResponse> getMemberFollowingList(
        @PathVariable("memberId") final Long memberId,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        FollowInfo.FollowingsResponse followingList = followFacade.getFollowingList(memberId, pageRequest);
        var response = followDtoMapper.of(followingList);
        return ResponseEntity.ok(response);
    }
}
