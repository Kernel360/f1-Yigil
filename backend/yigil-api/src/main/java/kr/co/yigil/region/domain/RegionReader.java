package kr.co.yigil.region.domain;

import java.util.List;

public interface RegionReader {

    void validateRegions(List<Long> regionIds);

    List<Region> getRegions(List<Long> favoriteRegionIds);
}
