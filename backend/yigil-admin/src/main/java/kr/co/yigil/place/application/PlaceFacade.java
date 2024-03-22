package kr.co.yigil.place.application;

import java.util.List;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.domain.PlaceInfo.Map;
import kr.co.yigil.place.domain.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PlaceFacade {
    private final PlaceService placeService;

    public List<Map> getPlaces(PlaceCommand.PlaceMapCommand command) {
        return placeService.getPlaces(command);
    }

    public Place getPlaceDetail(Long placeId) {
        return placeService.getPlaceDetail(placeId);
    }

    public void updateImage(PlaceCommand.UpdateImageCommand command) {
        placeService.updateImage(command);
    }

}
