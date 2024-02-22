package kr.co.yigil.travel.domain.spot;

import static kr.co.yigil.file.FileUploadUtil.predictAttachFile;

import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.util.Elements.Origin;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.util.GeojsonConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class SpotCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterSpotRequest {

        private final String pointJson;
        private final String title;
        private final String description;
        private final double rate;
        private final List<MultipartFile> files;
        private final RegisterPlaceRequest registerPlaceRequest;

        public Spot toEntity(Member member, Place place, boolean isInCourse) {

            var attachFiles = new AttachFiles(files.stream()
                    .map(FileUploadUtil::predictAttachFile)
                    .collect(Collectors.toList()));

            return new Spot(
                    member,
                    GeojsonConverter.convertToPoint(pointJson),
                    isInCourse,
                    title,
                    description,
                    attachFiles,
                    place,
                    rate
            );
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterPlaceRequest {

        private final MultipartFile mapStaticImageFile;
        private final MultipartFile placeImageFile;
        private final String placeName;
        private final String placeAddress;
        private final String placePointJson;

        public Place toEntity() {
            var mapStaticImage = predictAttachFile(mapStaticImageFile);
            var placeImage = predictAttachFile(placeImageFile);
            return new Place(placeName, placeAddress,
                    GeojsonConverter.convertToPoint(placePointJson), placeImage, mapStaticImage);
        }
    }

    @Getter
    @Builder
    @ToString
    public static class ModifySpotRequest {
        private final double rate;
        private final String description;
        private final List<OriginalSpotImage> originalImages;
        private final List<UpdateSpotImage> updatedImages;


    }

    @Getter
    @Builder
    @ToString
    public static class OriginalSpotImage {
        private String imageUrl;
        private int index;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateSpotImage {
        private MultipartFile imageFile;
        private int index;
    }

    @Getter
    @Builder
    @ToString
    public static class IndexedAttachFiles {
        private AttachFile file;
        private int index;
    }

}
