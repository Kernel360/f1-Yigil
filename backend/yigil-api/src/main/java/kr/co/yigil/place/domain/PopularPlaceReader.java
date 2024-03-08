package kr.co.yigil.place.domain;

import java.util.List;

public interface PopularPlaceReader {
    List<Place> getPopularPlace();

    List<Place> getPopularPlaceMore();
}
