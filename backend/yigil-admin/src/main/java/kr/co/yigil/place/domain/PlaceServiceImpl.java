package kr.co.yigil.place.domain;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.domain.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final FileUploader fileUploader;
    private final PlaceReader placeReader;

    @Override
    @Transactional(readOnly = true)
    public Page<Place> getPlaces(Pageable pageRequest) {
        return placeReader.getPlaces(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Place getPlaceDetail(Long placeId) {
        return placeReader.getPlace(placeId);
    }

    @Override
    @Transactional
    public void updateImage(PlaceCommand.UpdateImageCommand command) {
        Place place = placeReader.getPlace(command.getPlaceId());
        AttachFile updatedImage = fileUploader.upload(command.getImageFile());
        place.updateImage(updatedImage);
    }

}
