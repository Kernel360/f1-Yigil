package kr.co.yigil.travel.spot.interfaces.controller;


import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.travel.spot.application.SpotFacade;
import kr.co.yigil.travel.spot.interfaces.dto.SpotDto;
import kr.co.yigil.travel.spot.interfaces.dto.mapper.SpotDtoMapper;
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
public class SpotApiController {

    private final SpotFacade spotFacade;
    private final SpotDtoMapper spotDtoMapper;

    @GetMapping
    public ResponseEntity<SpotDto.AdminSpotsResponse> getSpots(
        @PageableDefault(size = 5, page = 1) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
            pageable.getPageSize(),
            Sort.by(direction, sortBy.getValue()));
        var spots = spotFacade.getSpots(pageRequest);
        var response = spotDtoMapper.of(spots);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<SpotDto.AdminSpotDetailResponse> getSpot(@PathVariable Long spotId) {
        var spot = spotFacade.getSpot(spotId);
        var response = spotDtoMapper.of(spot);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{spotId}")
    public ResponseEntity<SpotDto.AdminSpotDeleteResponse> deleteSpot(
        @PathVariable Long spotId) {
        spotFacade.deleteSpot(spotId);
        return ResponseEntity.ok().body(new SpotDto.AdminSpotDeleteResponse("삭제 성공"));
    }
}
