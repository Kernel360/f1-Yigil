package kr.co.yigil.travel.infrastructure.course;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
                    Place place = optionalPlace.orElseGet(() -> registerNewPlace(registerPlaceRequest));

                    var spot = spotStore.store(registerSpotRequest.toEntity(member, place, true));
                    var attachFiles = spot.getAttachFiles().getFiles();
                    var requestFiles = registerSpotRequest.getFiles();
                    for(var attachFile : attachFiles) {
                        for(var file : requestFiles) {
                            if(attachFile.getOriginalFileName().equals(file.getOriginalFilename())) {
                                fileUploader.upload(file, attachFile.getFileName());
                            }
                        }
                    }
                    var spotCount = placeCacheStore.incrementSpotCountInPlace(place.getId());
                    return spot;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Spot> store(RegisterCourseRequestWithSpotInfo request, Long memberId) {
        List<Spot> spots = spotReader.getSpots(request.getSpotIds());
        spots.forEach(Spot::changeInCourse);
        return spots;
    }

    private Place registerNewPlace(RegisterPlaceRequest command) {
        var place = placeStore.store(command.toEntity());
        fileUploader.upload(command.getPlaceImageFile(), place.getImageFile().getFileName());
        fileUploader.upload(command.getMapStaticImageFile(), place.getMapStaticImageFile().getFileName());
        return place;
    }
}
