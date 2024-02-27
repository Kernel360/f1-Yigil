package kr.co.yigil.place.domain;

import java.util.List;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.domain.PlaceInfo.Main;

public interface PlaceService {

    public List<Main> getPopularPlace(Accessor accessor);
    public PlaceInfo.MapStaticImageInfo findPlaceStaticImage(String placeName, String address);
}
