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

        // 코스를 저장
        Course course = CourseCreateRequest.toEntity(courseCreateRequest, spots);
        courseRepository.save(course);
        postService.createPost(course, member);

        //코스에 포함된 spot들 isIncourse 속성 true로 변경
        course.getSpots().forEach(spot -> spot.setIsInCourse(true));

        // 코스에 포함된 스팟을 담은 포스트 삭제
        spotIdList.forEach(spotId -> postService.deleteOnlyPost(memberId, spotId));

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

        // 코스에 스팟을 넣을 때 Post는 삭제하고 spot 정보는 업데이트.
        for(Long id: courseUpdateRequest.getAddedSpotIds()){
            postService.deleteOnlyPost(memberId, id);
            Spot spot = spotService.findSpotById(id);
            spot.setIsInCourse(true);
        }

        // 코스에 있던 spot을 뺄때 다시 post에 등록, post 필드의 deleted 가 true인 것을 false로 변경
        for(Long id: courseUpdateRequest.getRemovedSpotIds()){
            // spotId와 member Id로 post를 찾아서 해당 포스트의 deleted 필드를 false로 변경
            postService.recreatePost(memberId, id);
            Spot spot = spotService.findSpotById(id);
            spot.setIsInCourse(false);
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
