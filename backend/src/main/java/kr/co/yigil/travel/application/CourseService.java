package kr.co.yigil.travel.application;

import java.util.List;

import java.util.stream.Collectors;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.CourseRepository;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import org.springframework.stereotype.Service;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final PostRepository postRepository;
    private final SpotRepository spotRepository;
    private final TravelRepository travelRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createCourse(Long memberId, CourseCreateRequest courseCreateRequest) {
        Member member = findMemberById(memberId);
        List<Long> spotIdList = courseCreateRequest.getSpotIds();

        List<Spot> spots = spotIdList.stream()
                .map(this::findSpotById)
                .map(Spot.class::cast)
                .toList();

        // 코스에 포함된 스팟을 담은 포스트 삭제
        for(Long id : spotIdList) {
            postRepository.deleteByTravelId(id);
        }

        Course course = CourseCreateRequest.toEntity(courseCreateRequest, spots);
        courseRepository.save(course);

        return postRepository.save(new Post(course, member)).getId();
    }

    public CourseResponse findCourse(Long postId) {
        Post post = findPostById(postId);
        if(post.getTravel() instanceof Course course) {
            List<Spot> spots = course.getSpots();
            return CourseResponse.from(post, course, spots);
        }else {
            throw new BadRequestException(ExceptionCode.POST_NOT_CONTAIN_COURSE);
        }
    }

    public CourseResponse updateCourse(Long postId, Long memberId, CourseUpdateRequest courseUpdateRequest) {

        Member member = findMemberById(memberId);
        List<Long> spotIdList = courseUpdateRequest.getSpotIds();
        List<Spot> spots = travelRepository.findAllById(spotIdList).stream().map(Spot.class::cast).toList();

        // 추가된 course에 추가된 spot id는 course에 추가 후 post에서 삭제
        
        // todo:  삭제된 spot은 spot post로 다시 올리기
        List<Travel> deletedSpots = courseUpdateRequest.getRemovedSpotIds().stream()
                .map(this::findSpotById)
                .toList()
                ;
        // 이거 post에 등록해줘야함
        for(Travel spot : deletedSpots){
            Post newPost = new Post(spot, member);
            postRepository.save(newPost);
        }

        // 코스에 포함된 스팟을 담은 포스트 삭제
        for(Long id : courseUpdateRequest.getAddedSpotIds()) {
            postRepository.deleteByTravelId(id);
        }

        // 코스 정보 업데이트
        Course course = CourseUpdateRequest.toEntity(courseUpdateRequest, spots);
        courseRepository.save(course);
        // 그거를 이제 post에도 업데이트.. 근데 이거말고 cascade 쓸수잇나
        Post post = new Post(postId, course, member);
        postRepository.save(post);
        return CourseResponse.from(post, course, spots);
    }

    @Transactional
    public void deleteCourse(Long postId) {
        // 코스를 포함하는 포스트를 삭제할 때, 해당 포스트와 travel 모두 삭제한다고 가정
        Post post = findPostById(postId);

        Travel travel = post.getTravel();
        if(travel instanceof Spot){
            throw new BadRequestException(ExceptionCode.POST_NOT_CONTAIN_COURSE);
        }
//        travelRepository.delete(travel); // todo 이게맞나..? 팔로 팔로미
        postRepository.delete(post);
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

    private Travel findSpotById(Long spotId){
        return spotRepository.findById(spotId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID)
        );
    }


}
