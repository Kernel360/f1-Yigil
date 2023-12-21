package kr.co.yigil.travel.application;

import jakarta.transaction.Transactional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.post.dto.request.SpotRequest;
import kr.co.yigil.post.dto.response.SpotResponse;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createSpotPost(Long memberId, SpotRequest spotRequest) {
        Member member = findMemberById(memberId);
        Spot spotTravel = SpotRequest.toEntity(spotRequest);
        spotRepository.save(spotTravel);
        return postRepository.save(Post.of(spotTravel, member)).getId();
    }
    // todo: post로 감싸지 않은 findSpot 메서드 필요


    public SpotResponse findSpot(Long postId) {
        Post post = findPostById(postId);
        Spot spot = (Spot) post.getTravel();
        return SpotResponse.from(spot);
    }

    @Transactional
    public Long updateSpot(Long memberId, Long postId, SpotRequest spotRequest) {
        Member member = findMemberById(memberId);
        Travel spotTravel = SpotRequest.toEntity(spotRequest);
        spotRepository.save(spotTravel);
        var post = new Post(postId, spotTravel, member);

        return postRepository.save(post).getId();
    }

    @Transactional
    public void deleteSpot(Long postId){

        Post post = findPostById(postId);
        Travel travel = post.getTravel();
        spotRepository.delete(travel);
        postRepository.deleteById(postId);
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID)
        );
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                // todo: custome error code 정의
                () -> new RuntimeException("해당하는 포스트가 없습니다.")
        );
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
