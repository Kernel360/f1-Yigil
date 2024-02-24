package kr.co.yigil.travel.infrastructure;

import java.util.List;
import java.util.Objects;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.member.domain.MemberInfo.CoursesVisibilityResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseReaderImpl implements CourseReader {
    private final CourseRepository courseRepository;

    @Override
    public MemberInfo.MemberCourseResponse findAllByMemberId(Long memberId, Pageable pageable,
        String selectedInfo) {
        Page<Course> pageMember;
        if (selectedInfo.equals("all")) {
            pageMember = courseRepository.findAllByMemberId(memberId, pageable);
        } else {
            pageMember = courseRepository.findAllByMemberIdAndIsPrivate(memberId, selectedInfo.equals("private"), pageable);
        }
        pageMember = courseRepository.findAllByMemberId(memberId, pageable);
        List<MemberInfo.CourseInfo> courseInfoList = pageMember.getContent().stream()
            .map(MemberInfo.CourseInfo::new)
            .toList();
        return new MemberInfo.MemberCourseResponse(courseInfoList, pageMember.getTotalPages());
    }

    @Override
    public CoursesVisibilityResponse setCoursesVisibility(Long memberId, List<Long> courseIds,
        boolean isPrivate) {
        courseRepository.findAllById(courseIds)
            .forEach(course -> {
                if (!Objects.equals(course.getMember().getId(), memberId)) {
                    throw new AuthException(ExceptionCode.INVALID_AUTHORITY);
                }
                if (course.isPrivate() == isPrivate) {
                    throw new BadRequestException(ExceptionCode.INVALID_VISIBILITY_REQUEST);
                }
                if (course.isPrivate()) {
                    course.changeOnPublic();
                } else {
                    course.changeOnPrivate();
                }

            });
        return new CoursesVisibilityResponse("코스 공개범위가 변경되었습니다.");
    }
}
