package kr.co.yigil.place.domain;

import java.util.List;
import kr.co.yigil.place.domain.PlaceInfo.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {

    List<Map> getPlaces(PlaceCommand.PlaceMapCommand command);

    Place getPlaceDetail(Long placeId);

    void updateImage(PlaceCommand.UpdateImageCommand command);

}
