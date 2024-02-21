package kr.co.yigil.travel.application;

import kr.co.yigil.file.FileUploader;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import kr.co.yigil.travel.domain.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotFacade {
    private final SpotService spotService;
    private final FileUploader fileUploader;

    public Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable) {
        return spotService.getSpotSliceInPlace(placeId, pageable);
    }

    public void registerSpot(RegisterSpotRequest request, Long memberId) {
        spotService.registerSpot(request, memberId);
        request.getFiles().forEach(fileUploader::upload);
    }

    public Main retrieveSpotInfo(Long spotId) {
        return spotService.retrieveSpotInfo(spotId);
    }

    public void deleteSpot(Long spotId, Long memberId) {
        spotService.deleteSpot(spotId, memberId);
    }
}
