package kr.co.yigil.travel.infrastructure.course;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCacheStore;
import kr.co.yigil.place.domain.PlaceReader;
import kr.co.yigil.place.domain.PlaceStore;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.course.CourseSpotSeriesFactory;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterPlaceRequest;
import kr.co.yigil.travel.domain.spot.SpotReader;
import kr.co.yigil.travel.domain.spot.SpotStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseSpotSeriesFactoryImpl implements CourseSpotSeriesFactory {
    private final MemberReader memberReader;
    private final PlaceReader placeReader;
    private final SpotReader spotReader;

    private final PlaceStore placeStore;
    private final SpotStore spotStore;
    private final PlaceCacheStore placeCacheStore;

    private final FileUploader fileUploader;
    @Override
    public List<Spot> store(RegisterCourseRequest request, Long memberId) {
        var courseSpotRequestList = request.getRegisterSpotRequests();
        if (CollectionUtils.isEmpty(courseSpotRequestList)) return Collections.emptyList();
        Member member = memberReader.getMember(memberId);

        return courseSpotRequestList.stream()
                .map(registerSpotRequest -> {
                    var registerPlaceRequest = registerSpotRequest.getRegisterPlaceRequest();
                    Optional<Place> optionalPlace = placeReader.findPlaceByNameAndAddress(registerPlaceRequest.getPlaceName(), registerPlaceRequest.getPlaceAddress());

                    var attachFiles = new AttachFiles(registerSpotRequest.getFiles().stream()
                            .map(fileUploader::upload)
                            .collect(Collectors.toList()));

                    Place place = optionalPlace.orElseGet(() -> {
                        AttachFile placeAttachFile = new AttachFile(
                                attachFiles.getRepresentativeFile().getFileType(),
                                attachFiles.getRepresentativeFile().getFileUrl(),
                                attachFiles.getRepresentativeFile().getOriginalFileName(),
                                attachFiles.getRepresentativeFile().getFileSize()
                        );
                        return registerNewPlace(registerPlaceRequest, placeAttachFile);
                    });

                    placeCacheStore.incrementSpotCountInPlace(place.getId());
                    placeCacheStore.incrementSpotTotalRateInPlace(place.getId(), registerSpotRequest.getRate());
                    var spot = spotStore.store(registerSpotRequest.toEntity(member, place, true, attachFiles));

                    return spot;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Spot> store(RegisterCourseRequestWithSpotInfo request, Long memberId) {
        List<Spot> spots = spotReader.getMemberSpots(memberId, request.getSpotIds());
        spots.forEach(Spot::changeInCourse);
        return spots;
    }

    private Place registerNewPlace(RegisterPlaceRequest command, AttachFile placeImage) {

        var mapStaticImage = fileUploader.upload(command.getMapStaticImageFile());
        return placeStore.store(command.toEntity(placeImage, mapStaticImage));
    }
}
