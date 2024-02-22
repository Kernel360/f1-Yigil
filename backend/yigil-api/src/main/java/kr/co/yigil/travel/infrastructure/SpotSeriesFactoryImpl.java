package kr.co.yigil.travel.infrastructure;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileReader;
import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotSeriesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotSeriesFactoryImpl implements SpotSeriesFactory {

    private final FileReader fileReader;

    @Override
    public Spot modify(ModifySpotRequest command, Spot spot) {
        List<CombinedImage> spotComibinedImageList = command.getOriginalImages().stream()
                .map(image -> new CombinedImage(
                        fileReader.getFileByUrl(image.getImageUrl()), image.getIndex()))
                .collect(Collectors.toCollection(LinkedList::new));
        if(command.getUpdatedImages() != null) {
            spotComibinedImageList.addAll(command.getUpdatedImages().stream()
                    .map(image -> new CombinedImage(
                            FileUploadUtil.predictAttachFile(image.getImageFile()), image.getIndex()))
                    .toList());
        }


        spotComibinedImageList.sort(Comparator.comparingInt(CombinedImage::index));

        LinkedList<CombinedImage> sortedImages = new LinkedList<>(spotComibinedImageList);

        LinkedList<AttachFile> attachFileLinkedList = sortedImages.stream()
                .map(CombinedImage::attachFile)
                .collect(Collectors.toCollection(LinkedList::new));

        spot.updateSpot(command.getRate(), command.getDescription(), attachFileLinkedList);

        return spot;
    }
        private record CombinedImage(AttachFile attachFile, int index) {

    }
}
