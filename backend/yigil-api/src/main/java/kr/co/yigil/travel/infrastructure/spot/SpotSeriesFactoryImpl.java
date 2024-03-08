package kr.co.yigil.travel.infrastructure.spot;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileReader;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotSeriesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotSeriesFactoryImpl implements SpotSeriesFactory {

    private final FileReader fileReader;
    private final FileUploader fileUploader;

    @Override
    public Spot modify(ModifySpotRequest command, Spot spot) {
        List<CombinedImage> spotComibinedImageList = command.getOriginalImages().stream()
                .map(image -> new CombinedImage(
                        fileReader.getFileByUrl(image.getImageUrl()), image.getIndex()))
                .collect(Collectors.toCollection(LinkedList::new));
        if(command.getUpdatedImages() != null) {
            spotComibinedImageList.addAll(command.getUpdatedImages().stream()
                    .map(image -> new CombinedImage(
                            fileUploader.upload(image.getImageFile()), image.getIndex()))
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

    @Override
    public AttachFiles initAttachFiles(RegisterSpotRequest command) {
        return new AttachFiles(command.getFiles().stream()
                .map(fileUploader::upload)
                .collect(Collectors.toList()));

    }

    private record CombinedImage(AttachFile attachFile, int index) { }
}