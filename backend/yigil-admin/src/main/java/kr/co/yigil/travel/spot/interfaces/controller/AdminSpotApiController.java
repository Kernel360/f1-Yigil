package kr.co.yigil.travel.spot.interfaces.controller;


import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.travel.spot.application.AdminSpotFacade;
import kr.co.yigil.travel.spot.interfaces.dto.AdminSpotDto;
import kr.co.yigil.travel.spot.interfaces.dto.mapper.AdminSpotDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class AdminSpotApiController {

    private final AdminSpotFacade adminSpotFacade;
    private final AdminSpotDtoMapper adminSpotDtoMapper;

    @GetMapping
    public ResponseEntity<AdminSpotDto.AdminSpotsResponse> getSpots(
        @PageableDefault(size = 5, page = 1) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
            pageable.getPageSize(),
            Sort.by(direction, sortBy.getValue()));
        var spots = adminSpotFacade.getSpots(pageRequest);
        var response = adminSpotDtoMapper.of(spots);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<AdminSpotDto.AdminSpotDetailResponse> getSpot(@PathVariable Long spotId) {
        var spot = adminSpotFacade.getSpot(spotId);
        var response = adminSpotDtoMapper.of(spot);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{spotId}")
    public ResponseEntity<AdminSpotDto.AdminSpotDeleteResponse> deleteSpot(
        @PathVariable Long spotId) {
        adminSpotFacade.deleteSpot(spotId);
        return ResponseEntity.ok().body(new AdminSpotDto.AdminSpotDeleteResponse("삭제 성공"));
    }
}
