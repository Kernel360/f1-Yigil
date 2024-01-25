package kr.co.yigil.travel.application;

import java.util.List;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
<<<<<<< Updated upstream
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.Travel;
import kr.co.yigil.travel.repository.SpotRepository;
=======
import kr.co.yigil.place.Place;
import kr.co.yigil.place.dto.request.PlaceRequestDto;
import kr.co.yigil.place.repository.PlaceRepository;
import kr.co.yigil.travel.Spot;
>>>>>>> Stashed changes
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotFindDto;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import kr.co.yigil.travel.dto.response.SpotFindListResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import kr.co.yigil.travel.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final MemberService memberService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CommentService commentService;
    private final PlaceRepository placeRepository;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;

    @Transactional
    public SpotCreateResponse createSpot(Long memberId, SpotCreateRequest spotCreateRequest) {
        Member member = memberService.findMemberById(memberId);

<<<<<<< Updated upstream
//        Spot spot = spotRepository.save(SpotCreateRequest.toEntity(spotCreateRequest, "fileUrl"));
//        postService.createPost(spot, member);

        FileUploadEvent event = new FileUploadEvent(this, spotCreateRequest.getFile(),
            fileUrl -> {
                System.out.println("fileUrl = " + fileUrl);
                Spot spot = spotRepository.save(SpotCreateRequest.toEntity(spotCreateRequest));
                postService.createPost(spot, member);
            });
=======
        FileUploadEvent event = new FileUploadEvent(this, spotCreateRequest.getFiles(),
                attachFiles -> {
                    Place place = placeRepository.findByName(
                                    spotCreateRequest.getPlaceRequestDto().getName())
                            .orElseGet(
                                    () -> placeRepository.save(PlaceRequestDto.toEntity(
                                            spotCreateRequest.getPlaceRequestDto()))
                            );
                    spotRepository.save(SpotCreateRequest.toEntity(member, place, spotCreateRequest, attachFiles));
                });
>>>>>>> Stashed changes
        applicationEventPublisher.publishEvent(event);
        return new SpotCreateResponse("스팟 정보 생성 성공");
    }

    @Transactional(readOnly = true)
<<<<<<< Updated upstream
    public SpotFindResponse findSpotByPostId(Long postId) {
        Post post = postService.findPostById(postId);
        Member member = post.getMember();
        Spot spot = castTravelToSpot(post.getTravel());

        List<CommentResponse> comments = commentService.getCommentList(spot.getId());
        return SpotFindResponse.from(member, spot, comments);
=======
    public SpotFindResponse getSpot(Long spotId) {
        Spot spot = findSpotById(spotId);
        List<CommentResponse> comments = commentService.getCommentList(spotId);
        return SpotFindResponse.from(spot, comments); // todo 세부 필드 추가
>>>>>>> Stashed changes
    }

    @Transactional
    public SpotUpdateResponse updateSpot(Long memberId, Long spotId,
            SpotUpdateRequest spotUpdateRequest) {
        Spot spot = findSpotByIdAndMemberId(spotId, memberId);

        FileUploadEvent event = new FileUploadEvent(this, spotUpdateRequest.getFiles(),
                attachFiles -> {
                    spot.update(attachFiles, spotUpdateRequest.getTitle(),
                            spotUpdateRequest.getDescription(), spotUpdateRequest.getRate());
                });
        applicationEventPublisher.publishEvent(event);

<<<<<<< Updated upstream
        String fileUrl = fileUploadResult.join();
        // 기존 포스트의 spot 정보
        Spot spot = castTravelToSpot(post.getTravel());
        Long spotId = spot.getId();
        Spot newSpot = SpotUpdateRequest.toEntity(spotId, spotUpdateRequest);
        Spot updatedSpot = spotRepository.save(newSpot);

        Member member = memberService.findMemberById(memberId);
        postService.updatePost(postId, updatedSpot, member);
=======
        return new SpotUpdateResponse("스팟 정보 수정 성공");
    }
>>>>>>> Stashed changes

    public SpotFindListResponse getSpotList(Long placeId) {
        List<Spot> spots = spotRepository.findAllByPlaceIdAndIsInCourseFalse(placeId);
        List<SpotFindDto> spotFindDtoList = spots.stream()
                .map(spot -> SpotFindDto.from(spot,1,1))
                .toList();
        return SpotFindListResponse.from(spotFindDtoList); // todo 페이징 적용
    }
    @Transactional(readOnly = true)
    public Spot findSpotByIdAndMemberId(Long spotId, Long memberId) {
        return spotRepository.findByIdAndMemberId(spotId, memberId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID)
        );
    }

    @Transactional(readOnly = true)
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


