package kr.co.yigil.travel.application;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileUploadEvent;
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
import kr.co.yigil.travel.dto.response.CourseInfoResponse;
import kr.co.yigil.travel.dto.response.CourseUpdateResponse;
import kr.co.yigil.travel.repository.CourseRepository;
import kr.co.yigil.travel.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final SpotRepository spotRepository;
    private final MemberService memberService;
    private final SpotService spotService;
    private final CommentService commentService;
    private final FavorRedisIntegrityService favorRedisIntegrityService;
    private final CommentRedisIntegrityService commentRedisIntegrityService;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    public CourseCreateResponse createCourse(Long memberId, CourseCreateRequest courseCreateRequest) {
        Member member = memberService.findMemberById(memberId);
        List<Long> spotIdList = courseCreateRequest.getSpotIds();
        List<Spot> spots = spotService.getSpotListFromSpotIds(spotIdList);

        AttachFile attachFile = getAttachFile(courseCreateRequest.getMapStaticImageFile());

        Course course = CourseCreateRequest.toEntity(member, courseCreateRequest, spots, attachFile);
        courseRepository.save(course);

        course.getSpots().forEach(spot -> spot.setInCourse(true));

        return new CourseCreateResponse(course.getId(), "경로 생성 성공");
    }

    @Transactional(readOnly = true)
    public CourseInfoResponse getCourseInfo(Long courseId) {
        Course course = findCourseById(courseId);
        List<Spot> spots = course.getSpots();

        List<CommentResponse> comments = commentService.getCommentList(course.getId());
        return CourseInfoResponse.from(course, spots, comments);
    }

    @Transactional
    public Slice<CourseFindDto> getCourseList(Long placeId, Pageable pageable) {
        Slice<Course> courses = courseRepository.findBySpotPlaceId(placeId, pageable);
        List<CourseFindDto> courseFindDtoList = courses.stream()
                .map(this::getCourseFindDto)
                .toList();
        return new SliceImpl<>(courseFindDtoList, pageable, courses.hasNext());
    }

    @Transactional
    public CourseUpdateResponse updateCourse(Long courseId, Long memberId, CourseUpdateRequest courseUpdateRequest) {
        Member member = memberService.findMemberById(memberId);
        List<Long> spotIdList = courseUpdateRequest.getSpotIds();
        List<Spot> spots = spotRepository.findAllById(spotIdList);

        for(Long id: courseUpdateRequest.getAddedSpotIds()){
            Spot spot = spotService.findSpotById(id);
            spot.setInCourse(true);
        }

        for(Long id: courseUpdateRequest.getRemovedSpotIds()){
            Spot spot = spotService.findSpotById(id);
            spot.setInCourse(false);
        }

        AttachFile attachFile = getAttachFile(courseUpdateRequest.getMapStaticImageFile());
        Course newCourse = CourseUpdateRequest.toEntity(member, courseId, courseUpdateRequest, spots, attachFile);
        courseRepository.save(newCourse);
        return new CourseUpdateResponse("경로 수정 성공");
    }

    @Transactional
    public CourseDeleteResponse deleteCourse(Long courseId, Long memberId) {
        Course course = courseRepository.findByIdAndMemberId(courseId, memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID));
        courseRepository.delete(course);
        return new CourseDeleteResponse("경로 삭제 성공");
    }

    @NotNull
    private CourseFindDto getCourseFindDto(Course course) {
        Integer favorCount = favorRedisIntegrityService.ensureFavorCounts(course).getFavorCount();
        Integer commentCount = commentRedisIntegrityService.ensureCommentCount(course).getCommentCount();
        return CourseFindDto.from(course, favorCount, commentCount);
    }

    private Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_COURSE_ID));
    }

    private AttachFile getAttachFile(MultipartFile mapStaticImageFile) {
        CompletableFuture<AttachFile> fileCompletableFuture = new CompletableFuture<>();
        FileUploadEvent event = new FileUploadEvent(this, mapStaticImageFile,
                fileCompletableFuture::complete);
        applicationEventPublisher.publishEvent(event);
        return fileCompletableFuture.join();
    }
}
