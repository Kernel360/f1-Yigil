package kr.co.yigil.place.domain;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceReader {

    List<Place> getPlaces(double startX, double startY, double endX, double endY);

    Place getPlace(Long placeId);
}
