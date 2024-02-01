package kr.co.yigil.travel.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.application.PlaceService;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.dto.response.SpotFindDto;
import kr.co.yigil.travel.dto.response.SpotInfoResponse;
import kr.co.yigil.travel.dto.response.SpotListResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import kr.co.yigil.travel.repository.SpotRepository;
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
    private final CommentService commentService;
    private final PlaceService placeService;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;

    @Transactional(readOnly = true)
    public SpotListResponse getSpotList(Long placeId) {
        List<Spot> spots = spotRepository.findAllByPlaceIdAndIsInCourseFalse(placeId);
        List<SpotFindDto> spotFindDtoList = spots.stream()
                .map(spot -> SpotFindDto.from(
                        spot,
                        favorRedisIntegrityService.ensureFavorCounts(spot).getFavorCount(),
                        commentRedisIntegrityService.ensureCommentCount(spot).getCommentCount())
                )
                .toList();
        return SpotListResponse.from(spotFindDtoList); // todo 페이징 적용
    }

    @Transactional
    public SpotCreateResponse createSpot(Long memberId, SpotCreateRequest spotCreateRequest) {
        Member member = memberService.findMemberById(memberId);

        // spot 생성
        AttachFiles attachFiles = getAttachFiles(spotCreateRequest.getFiles());
        AttachFile mapStaticImageFile = getAttachFile(spotCreateRequest.getMapStaticImageFile());

        Place place = placeService.getOrCreatePlace(
                spotCreateRequest.getPlaceName(),
                spotCreateRequest.getPlaceAddress(),
                spotCreateRequest.getPlacePointJson()
        );
        Spot spot = spotRepository.save(
                SpotCreateRequest.toEntity(member, place, spotCreateRequest, attachFiles, mapStaticImageFile));

        return new SpotCreateResponse(spot.getId(), "스팟 정보 생성 성공");
    }

    @Transactional(readOnly = true)
    public SpotInfoResponse getSpotInfo(Long spotId) {
        Spot spot = findSpotById(spotId);
        List<CommentResponse> comments = commentService.getCommentList(spotId);
        return SpotInfoResponse.from(spot, comments);
    }

    @Transactional
    public SpotUpdateResponse updateSpot(Long memberId, Long spotId,
            SpotUpdateRequest spotUpdateRequest) {
        Member member = memberService.findMemberById(memberId);
        AttachFiles attachFiles = getAttachFiles(spotUpdateRequest.getFiles());
        AttachFile mapStaticImageFile = getAttachFile(spotUpdateRequest.getMapStaticImageFile());

        Place place = placeService.getOrCreatePlace(
                spotUpdateRequest.getPlaceName(),
                spotUpdateRequest.getPlaceAddress(),
                spotUpdateRequest.getPlacePointJson()
        );
        spotRepository.save(
                SpotUpdateRequest.toEntity(member, spotId, spotUpdateRequest, place, attachFiles, mapStaticImageFile));

        return new SpotUpdateResponse("스팟 정보 수정 성공");
    }

    @Transactional
    public SpotDeleteResponse deleteSpot(Long memberId, Long spotId) {
        Spot spot = findSpotByIdAndMemberId(spotId, memberId);
        spotRepository.delete(spot);
        // todo 댓글, 좋아요 삭제
        return new SpotDeleteResponse("스팟 정보 삭제 성공");
    }

//    private AttachFiles getAttachFiles(List<MultipartFile> files) {
//        AttachFiles attachFiles = new AttachFiles(new ArrayList<>());
//        files.forEach(file -> {
//                    FileUploadEvent event = new FileUploadEvent(this, file, attachFiles::addFile);
//                    applicationEventPublisher.publishEvent(event);
//                }
//        );
//        return attachFiles;
//    }

    private AttachFiles getAttachFiles(List<MultipartFile> files) {
        List<CompletableFuture<AttachFile>> futures = new ArrayList<>();
        AttachFiles attachFiles = new AttachFiles(new ArrayList<>());


        files.forEach(
                file ->{
                    CompletableFuture<AttachFile> future = new CompletableFuture<>();
                    FileUploadEvent event = new FileUploadEvent(this, file, future::complete);
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
        FileUploadEvent event = new FileUploadEvent(this, mapStaticImageFile, fileCompletableFuture::complete);
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


