package kr.co.yigil.travel.application;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final PostRepository postRepository;

    @Transactional
    public SpotCreateResponse createSpot(Long memberId, SpotCreateRequest spotCreateRequest) {
        Member member = memberService.findMemberById(memberId);

        FileUploadEvent event = new FileUploadEvent(this, spotCreateRequest.getFile(),
            fileUrl -> {
                Spot spot = spotRepository.save(SpotCreateRequest.toEntity(spotCreateRequest, fileUrl));
                postService.createPost(spot, member);
            });

        applicationEventPublisher.publishEvent(event);
        return new SpotCreateResponse("스팟 정보 생성 성공");
    }

    @Transactional(readOnly = true)
    public SpotFindResponse findSpotByPostId(Long postId) {

        Post post = postService.findPostById(postId);
        Member member = post.getMember();
        Spot spot = castTravelToSpot(post.getTravel());
        return SpotFindResponse.from(member, spot);
    }

    @Transactional
    public SpotUpdateResponse updateSpot(Long memberId, Long postId, SpotUpdateRequest spotUpdateRequest) {

        Post post = postService.findPostById(postId);
        postService.validatePostWriter(memberId, postId);

        CompletableFuture<String> fileUploadResult = new CompletableFuture<>();
        FileUploadEvent event = new FileUploadEvent(this, spotUpdateRequest.getFile(),fileUrl -> {
            fileUploadResult.complete(fileUrl);
        }
        );
        applicationEventPublisher.publishEvent(event);

        String fileUrl = fileUploadResult.join();
        // 기존 포스트의 spot 정보
        Spot spot = castTravelToSpot(post.getTravel());
        Long spotId = spot.getId();
        Spot newSpot = SpotUpdateRequest.toEntity(spotId, spotUpdateRequest, fileUrl);
        Spot updatedSpot = spotRepository.save(newSpot);

        Member member = memberService.findMemberById(memberId);
        postService.updatePost(postId, updatedSpot, member);

        return SpotUpdateResponse.from(member, updatedSpot);

    }

    @Transactional(readOnly = true)
    public Spot findSpotById(Long spotId){
        Travel travel = spotRepository.findById(spotId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID)
        );

        return castTravelToSpot(travel);
    }

    @Transactional(readOnly = true)
    public List<Spot> getSpotListFromSpotIds(List<Long> spotIdList){
        return spotIdList.stream()
            .map(this::findSpotById)
            .toList();
    }

     private Spot castTravelToSpot(Travel travel){
        if(travel instanceof Spot spot){
            return spot;
        }else{
            throw new BadRequestException(ExceptionCode.TRAVEL_CASTING_ERROR);
        }
    }
}


