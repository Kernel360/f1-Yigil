package kr.co.yigil.travel.application;

import java.util.List;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.dto.response.CourseFindResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.CourseRepository;
import kr.co.yigil.travel.dto.response.CourseUpdateResponse;
import org.springframework.stereotype.Service;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TravelRepository travelRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final SpotService spotService;

    @Transactional
    public CourseCreateResponse createCourse(Long memberId, CourseCreateRequest courseCreateRequest) {
        Member member = memberService.findMemberById(memberId);
        List<Long> spotIdList = courseCreateRequest.getSpotIds();
        List<Spot> spots = spotService.getSpotListFromSpotIds(spotIdList);

        // 코스에 포함된 스팟을 담은 포스트 삭제
         spotIdList.forEach(spotId -> postService.deleteOnlyPost(memberId, spotId));

        Course course = CourseCreateRequest.toEntity(courseCreateRequest, spots);
        courseRepository.save(course);
        postService.createPost(course, member);
        return new CourseCreateResponse("경로 생성 성공");
    }

    @Transactional(readOnly = true)
    public CourseFindResponse findCourse(Long postId) {
        Post post = postService.findPostById(postId);
        Course course = castTravelToCourse(post.getTravel());
        List<Spot> spots = course.getSpots();
        return CourseFindResponse.from(post, course, spots);
    }

    @Transactional
    public CourseUpdateResponse updateCourse(Long postId, Long memberId, CourseUpdateRequest courseUpdateRequest) {
        Post post = postService.findPostById(postId);
        postService.validatePostWriter(memberId, postId);

        Member member = memberService.findMemberById(memberId);
        List<Long> spotIdList = courseUpdateRequest.getSpotIds();
        List<Spot> spots = travelRepository.findAllById(spotIdList).stream().map(Spot.class::cast).toList();

        // 추가된 course에 추가된 spot id는 course에 추가 후 post에서 삭제
        List<Travel> deletedSpots = courseUpdateRequest.getRemovedSpotIds().stream()
            .map(spotService::findSpotById)
            .toList();

        // 이거 post에 등록해줘야함
        for(Travel spot: deletedSpots){
            postService.createPost(spot, member);
        }

        // 코스에 포함된 스팟을 담은 포스트 삭제
        for(Long id: courseUpdateRequest.getAddedSpotIds()){
            postService.deleteOnlyPost(memberId, id);
        }

        // 코스 정보 업데이트
        Course course = castTravelToCourse(post.getTravel());
        Long courseId = course.getId();

        Course newCourse = CourseUpdateRequest.toEntity(courseId, courseUpdateRequest, spots);
        Course updatedCourse = courseRepository.save(newCourse);

        postService.updatePost(postId, newCourse, member);
        return CourseUpdateResponse.from(member, updatedCourse, spots);
    }

    private Course castTravelToCourse(Travel travel) {
        if(travel instanceof Course course) {
            return course;
        }else {
            throw new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID);
        }
    }
}
