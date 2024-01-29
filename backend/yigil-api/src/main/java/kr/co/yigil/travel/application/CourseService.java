package kr.co.yigil.travel.application;

import java.util.List;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.travel.Course;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.dto.response.CourseDeleteResponse;
import kr.co.yigil.travel.dto.response.CourseFindDto;
import kr.co.yigil.travel.dto.response.CourseFindListResponse;
import kr.co.yigil.travel.dto.response.CourseFindResponse;
import kr.co.yigil.travel.dto.response.CourseUpdateResponse;
import kr.co.yigil.travel.repository.CourseRepository;
import kr.co.yigil.travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TravelRepository travelRepository;
    private final MemberService memberService;
    private final SpotService spotService;
    private final CommentService commentService;

    @Transactional(readOnly = true)
    public CourseFindListResponse getCourseList(Long placeId) {
        List<Course> courses = courseRepository.findBySpotPlaceId(placeId);
        List<CourseFindDto> courseFindDtoList = courses.stream()
                .map(course -> CourseFindDto.from(course, 1, 1)) // todo 좋아요, 댓글 수 추가
                .toList();
        return CourseFindListResponse.from(courseFindDtoList);
    }

    @Transactional
    public CourseCreateResponse createCourse(Long memberId, CourseCreateRequest courseCreateRequest) {
        Member member = memberService.findMemberById(memberId);
        List<Long> spotIdList = courseCreateRequest.getSpotIds();
        List<Spot> spots = spotService.getSpotListFromSpotIds(spotIdList);

        Course course = CourseCreateRequest.toEntity(member, courseCreateRequest, spots);
        courseRepository.save(course);

        course.getSpots().forEach(spot -> spot.setInCourse(true));

        return new CourseCreateResponse("경로 생성 성공");
    }

    @Transactional(readOnly = true)
    public CourseFindResponse getCourse(Long courseId) {
        Course course = findCourseById(courseId);
        List<Spot> spots = course.getSpots();

        List<CommentResponse> comments = commentService.getCommentList(course.getId());
        return CourseFindResponse.from(course, spots, comments);
    }

    private Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID));
    }

    @Transactional
    public CourseUpdateResponse updateCourse(Long courseId, Long memberId, CourseUpdateRequest courseUpdateRequest) {
        Member member = memberService.findMemberById(memberId);
        List<Long> spotIdList = courseUpdateRequest.getSpotIds();
        List<Spot> spots = travelRepository.findAllById(spotIdList).stream().map(Spot.class::cast).toList();

        for(Long id: courseUpdateRequest.getAddedSpotIds()){
            Spot spot = spotService.findSpotById(id);
            spot.setInCourse(true);
        }

        for(Long id: courseUpdateRequest.getRemovedSpotIds()){
            Spot spot = spotService.findSpotById(id);
            spot.setInCourse(false);
        }

        Course newCourse = CourseUpdateRequest.toEntity(member, courseId, courseUpdateRequest, spots);
        Course updatedCourse = courseRepository.save(newCourse);

        return CourseUpdateResponse.from(member, updatedCourse, spots);
    }

    @Transactional
    public CourseDeleteResponse deleteCourse(Long courseId, Long memberId) {
        Course course = courseRepository.findByIdAndMemberId(courseId, memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID));
        courseRepository.delete(course);
        return new CourseDeleteResponse("경로 삭제 성공");
    }
}
