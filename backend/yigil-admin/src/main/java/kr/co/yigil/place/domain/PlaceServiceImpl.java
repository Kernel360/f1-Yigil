package kr.co.yigil.place.domain;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.domain.FileUploader;
import kr.co.yigil.place.domain.PlaceCommand.PlaceMapCommand;
import kr.co.yigil.place.domain.PlaceInfo.Map;
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
    public List<PlaceInfo.Map> getPlaces(PlaceMapCommand command) {
        var places = placeReader.getPlaces(command.getStartX(), command.getStartY(), command.getEndX(), command.getEndY());
        return places.stream().map(Map::new).collect(Collectors.toList());
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
