package kr.co.yigil.travel.application;

import kr.co.yigil.file.FileUploader;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpot;
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

    public MySpot retrieveMySpotInfoInPlace(Long placeId, Long memberId) {
        return spotService.retrieveMySpotInfoInPlace(placeId, memberId);
    }

    public void registerSpot(RegisterSpotRequest command, Long memberId) {
        Spot spot = spotService.registerSpot(command, memberId);
        var attachFiles = spot.getAttachFiles();
        var files = command.getFiles();
        for(var attachFile : attachFiles.getFiles()) {
            for(var file : files) {
                if(attachFile.getOriginalFileName().equals(file.getOriginalFilename())) {
                    fileUploader.upload(file, attachFile.getFileName());
                }
            }
        }
    }

    public Main retrieveSpotInfo(Long spotId) {
        return spotService.retrieveSpotInfo(spotId);
    }

    public void modifySpot(ModifySpotRequest command, Long spotId, Long memberId) {
        var spot = spotService.modifySpot(command, spotId, memberId);
        var attachFiles = spot.getAttachFiles();
        var files = command.getUpdatedImages();
        for(var attachFile : attachFiles.getFiles()) {
            for(var file : files) {
                if(attachFile.getOriginalFileName().equals(file.getImageFile().getOriginalFilename())) {
                    fileUploader.upload(file.getImageFile(), attachFile.getFileName());
                }
            }
        }
    }

    public void deleteSpot(Long spotId, Long memberId) {
        spotService.deleteSpot(spotId, memberId);
    }

    public SpotInfo.MySpotsResponse getMemberSpotsInfo(final Long memberId, Pageable pageable,
        String selected) {
        return spotService.retrieveSpotList(memberId, pageable, selected);
    }
}
