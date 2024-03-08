package kr.co.yigil.travel.spot.infrastructure;


import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import kr.co.yigil.travel.spot.domain.SpotReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotReaderImpl implements SpotReader {

    private final SpotRepository spotRepository;


    @Override
    public Page<Spot> getSpots(Pageable pageable) {
        return spotRepository.findAll(pageable);
    }

    @Override
    public Spot getSpot(Long spotId) {
        return spotRepository.findById(spotId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID)
        );
    }
}
