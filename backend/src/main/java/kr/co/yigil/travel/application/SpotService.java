package kr.co.yigil.travel.application;

import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SpotCreateResponse createSpot(Long memberId, SpotCreateRequest spotCreateRequest) {
        Member member = findMemberById(memberId);
        Spot spot = spotRepository.save(SpotCreateRequest.toEntity(spotCreateRequest));
        Long postId = postRepository.save(new Post(spot, member)).getId();
        return new SpotCreateResponse(postId);
    }

    public SpotFindResponse findSpot(Long postId) {
        Post post = findPostById(postId);
        Member member = post.getMember();
        if (post.getTravel() instanceof Spot spot) {
            return SpotFindResponse.from(member, spot);
        } else throw new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID);
    }

    @Transactional
    public SpotUpdateResponse updateSpot(Long memberId, Long postId, SpotUpdateRequest spotUpdateRequest) {
        Post post = findPostById(postId);
        validatePostWriter(memberId, postId);
        Member member = findMemberById(memberId);
        
        // 기존 포스트의 spot 정보
        Long spotId = post.getTravel().getId();
        Spot spot = SpotUpdateRequest.toEntity(spotUpdateRequest);
        spot.setId(spotId);
        spotRepository.save(spot);

        return SpotUpdateResponse.from(member, spot);
    }

    @Transactional
    public SpotDeleteResponse deleteSpot(Long memberId, Long postId){
        Post post = findPostById(postId);
        validatePostWriter(memberId, postId);
        postRepository.delete(post);
        return new SpotDeleteResponse("spot 삭제 성공");
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID)
        );
    }
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_POST_ID)
        );
    }
    private void validatePostWriter(Long memberId, Long postId) {
        if (!postRepository.existsByMemberIdAndId(memberId, postId)) {
            throw new BadRequestException(ExceptionCode.HAS_NO_PERMISSION);
        }
    }
}

//
//.. cascade 고민
//-spot 생성할 때 post로 생성돼야함 /
//        근데 course안의 spot 생성할때는 post로 생성되면 안됨
//        -> 별도의 컨트롤러 생성. course id 가 요청에 포함되면 spot 생성하고 isInCourse 를 true로 변경,
//        -> Course Controller에서 add Spot 메서드를 만들고 여기서는 포스트가 아닌 spot을 생성하고
//        그냥 spot을 생성할 때는 spot controller에서 create으로 만든 다음에 post spot을 생성하면 되지 않을까유?
//- course 생성할 때 post로 생성돼야함
//
//수정 시
//- spot 게시글일때 : spot 수정 시 post 도 수정  이 아니네? post은 그냥 수정필요없음
//        아닌가? post도 수정돼야함..
//- course에 포함된 spot 일때 : spot 수정시 그냥 post에 없으니 post 수정 안됨
//
//- course 수정시 - post 도 수정
// 삭제 시
// - post 삭제 시 spot, course 삭제
