package kr.co.yigil.place.infrastructure;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PopularPlace;
import kr.co.yigil.place.domain.PopularPlaceReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopularPlaceReaderImpl implements PopularPlaceReader {
    private final PopularPlaceRepository popularPlaceRepository;

    @Override
    public List<Place> getPopularPlace() {
        return popularPlaceRepository.findTop5ByOrderByReferenceCountDesc()
                .stream()
                .map(PopularPlace::getPlace)
                .collect(Collectors.toList());
    }

    @Override
    public List<Place> getPopularPlaceMore() {
        return popularPlaceRepository.findTop20ByOrderByReferenceCountDesc()
                .stream()
                .map(PopularPlace::getPlace)
                .collect(Collectors.toList());
    }
}
