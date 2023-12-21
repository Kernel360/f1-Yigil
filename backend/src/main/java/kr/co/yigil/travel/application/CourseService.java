package kr.co.yigil.travel.application;

import java.util.List;

import java.util.stream.Collectors;
import kr.co.yigil.post.dto.request.CourseRequest;
import kr.co.yigil.post.dto.request.SpotRequest;
import kr.co.yigil.post.dto.response.CourseResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.repository.CourseRepository;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final PostRepository postRepository;
    private final SpotRepository spotRepository;
    private final TravelRepository travelRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createCourse(Long memberId, CourseRequest courseRequest) {
        Member member = findMemberById(memberId);
        List<Long> spotIdList = courseRequest.getSpotIds();

        List<Spot> spots = spotIdList.stream().map(
                id-> spotRepository.findById(id).orElseThrow(
                //todo: custom Error Message 만들기
                () -> new RuntimeException("spot이 없습니다.")
                )
        ).map(Spot.class::cast)
        .collect(Collectors.toList());

        // 코스에 포함된 스팟을 담은 포스트 삭제
        for(Long id : spotIdList) {
            postRepository.deleteByTravelId(id);
        }

        Course courseTravel = CourseRequest.toEntity(courseRequest, spots);
        courseRepository.save(courseTravel);

        return postRepository.save(Post.of(courseTravel, member)).getId();
    }

    public CourseResponse findCourse(Long postId) {
        Post post = findPostById(postId);
        Course course = (Course) post.getTravel();
        List<Spot> spots = course.getSpots();
        return CourseResponse.from(post);
    }

    public CourseResponse updateCourse(Long postId, Long memberId, CourseRequest courseRequest) {

        Member member = findMemberById(memberId);
        List<Long> spotIdList = courseRequest.getSpotIds();

        //todo: courseRequest 에 담는 정보가 course에 포함된 updated 된 일이라 가정.
//        List<Spot> spots = spotRepository.findAll()
        List<Spot> spots = travelRepository.findAllById(spotIdList).stream().map(Spot.class::cast).toList();

        // 추가된 course에 추가된 spot id는 course에 추가 후 post에서 삭제

        // todo:  삭제된 spot들은 어떻게 해야할지 논의 필요

        // 코스에 포함된 스팟을 담은 포스트 삭제
        for(Long id : spotIdList) {
            postRepository.deleteByTravelId(id);
        }

        // 코스 정보 업데이트
        Course course = CourseRequest.toEntity(courseRequest, spots);
        courseRepository.save(course);
        // 그거를 이제 post에도 업데이트.. 근데 이거말고 cascade 쓸수잇나
        Post post = Post.of(postId, course, member);
        postRepository.save(post);
        return CourseResponse.from(post);
    }
    @Transactional
    public void deleteCourse(Long postId) {

        // 코스를 포함하는 포스트를 삭제할 때, 해당 포스트와 travel 모두 삭제한다고 가정
        Post post = findPostById(postId);
        Spot travel = (Spot)(post.getTravel());
        travelRepository.delete(travel);
        postRepository.delete(post);
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
