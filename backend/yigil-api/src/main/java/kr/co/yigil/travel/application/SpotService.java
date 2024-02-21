package kr.co.yigil.travel.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.application.PlaceRateRedisIntegrityService;
import kr.co.yigil.place.application.PlaceService;
import kr.co.yigil.place.domain.PlaceRate;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCount;
import kr.co.yigil.travel.interfaces.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotFindDto;
import kr.co.yigil.travel.interfaces.dto.response.SpotInfoResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotUpdateResponse;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final MemberService memberService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PlaceService placeService;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;
    private final SpotRedisIntegrityService spotRedisIntegrityService;
    private final PlaceRateRedisIntegrityService placeRateRedisIntegrityService;

    private SpotFindDto getSpotFindDto(Spot spot) {
        return SpotFindDto.from(
                spot,
                favorRedisIntegrityService.ensureFavorCounts(spot).getFavorCount(),
                commentRedisIntegrityService.ensureCommentCount(spot).getCommentCount());
    }

    @Transactional
    public SpotCreateResponse createSpot(Long memberId, SpotCreateRequest spotCreateRequest) {
        Member member = memberService.findMemberById(memberId);

        // spot 생성
        AttachFiles attachFiles = getAttachFiles(spotCreateRequest.getFiles());
        AttachFile mapStaticImageFile = getAttachFile(spotCreateRequest.getMapStaticImageFile());
        AttachFile placeImageFile = getAttachFile(spotCreateRequest.getPlaceImageFile());

        Place place = getOrCreatePlace(
                spotCreateRequest.getPlaceName(),
                spotCreateRequest.getPlaceAddress(),
                spotCreateRequest.getPlacePointJson(),
                placeImageFile,
                mapStaticImageFile
        );

        checkAlreadyExist(memberId, place);

        incrementSpotCount(place);
        addRateToPlace(place, spotCreateRequest.getRate());

        Spot spot = spotRepository.save(
                SpotCreateRequest.toEntity(member, place, spotCreateRequest, attachFiles));

        return new SpotCreateResponse(spot.getId(), "스팟 정보 생성 성공");
    }

    private void addRateToPlace(Place place, double rate) {
        PlaceRate placeRate = placeRateRedisIntegrityService.ensurePlaceRate(place.getId());
        placeRate.addRate(rate);
    }

    private void incrementSpotCount(Place place) {
        SpotCount spotCount = spotRedisIntegrityService.ensureSpotCounts(place.getId());
        spotCount.incrementSpotCount();
    }

    private void checkAlreadyExist(Long memberId, Place place) {
        spotRepository.findByPlaceIdAndMemberId(place.getId(), memberId)
                .ifPresent(s -> {
                    throw new BadRequestException(ExceptionCode.ALREADY_EXIST_SPOT);
                });
    }


    private Place getOrCreatePlace(String placeName, String placeAddress,
            String placePointJson, AttachFile placeImageFile, AttachFile mapStaticImageFile) {
        return placeService.getOrCreatePlace(
                placeName,
                placeAddress,
                placePointJson,
                placeImageFile,
                mapStaticImageFile
        );
    }

    @Transactional(readOnly = true)
    public SpotInfoResponse getSpotInfo(Long spotId) {
        Spot spot = findSpotById(spotId);
        FavorCount favorCount = favorRedisIntegrityService.ensureFavorCounts(spot);
        CommentCount commentCount = commentRedisIntegrityService.ensureCommentCount(spot);
        return SpotInfoResponse.from(spot, favorCount.getFavorCount(), commentCount.getCommentCount());
    }

    @Transactional
    public SpotUpdateResponse updateSpot(Long memberId, Long spotId,
            SpotUpdateRequest spotUpdateRequest) {
        Member member = memberService.findMemberById(memberId);

        AttachFiles attachFiles = getAttachFiles(spotUpdateRequest.getFiles());

        Place place = placeService.getPlaceById(spotUpdateRequest.getPlaceId());

        spotRepository.save(
                SpotUpdateRequest.toEntity(member, spotId, spotUpdateRequest, place, attachFiles));

        return new SpotUpdateResponse("스팟 정보 수정 성공");
    }

    @Transactional
    public SpotDeleteResponse deleteSpot(Long memberId, Long spotId) {
        Spot spot = findSpotByIdAndMemberId(spotId, memberId);
        spotRepository.delete(spot);
        decrementSpotCount(spot.getPlace());
        removeRateFromPlace(spot.getPlace(), spot.getRate());
        return new SpotDeleteResponse("스팟 정보 삭제 성공");
    }

    private void removeRateFromPlace(Place place, double rate) {
        PlaceRate placeRate = placeRateRedisIntegrityService.ensurePlaceRate(place.getId());
        placeRate.removeRate(rate);
    }

    private void decrementSpotCount(Place place) {
        SpotCount spotCount = spotRedisIntegrityService.ensureSpotCounts(place.getId());
        spotCount.decrementSpotCount();
    }

    private AttachFiles getAttachFiles(List<MultipartFile> files) {
        List<CompletableFuture<AttachFile>> futures = new ArrayList<>();
        AttachFiles attachFiles = new AttachFiles(new ArrayList<>());

        files.forEach(
                file -> {
                    CompletableFuture<AttachFile> future = new CompletableFuture<>();
                    FileUploadEvent event = new FileUploadEvent(this, file);
                    applicationEventPublisher.publishEvent(event);
                    futures.add(future);
                }
        );

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<AttachFile> future : futures) {
            attachFiles.addFile(future.join());
        }

        return attachFiles;
    }




    private AttachFile getAttachFile(MultipartFile mapStaticImageFile) {
        CompletableFuture<AttachFile> fileCompletableFuture = new CompletableFuture<>();
        FileUploadEvent event = new FileUploadEvent(this, mapStaticImageFile);
        applicationEventPublisher.publishEvent(event);
        return fileCompletableFuture.join();
    }

    public Spot findSpotByIdAndMemberId(Long spotId, Long memberId) {
        return spotRepository.findByIdAndMemberId(spotId, memberId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID)
        );
    }

    public Spot findSpotById(Long spotId) {
        return spotRepository.findById(spotId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID)
        );
    }

    @Transactional(readOnly = true)
    public List<Spot> getSpotListFromSpotIds(List<Long> spotIdList) {
        return spotIdList.stream()
                .map(this::findSpotById)
                .toList();
    }
}


