package kr.co.yigil.travel.infrastructure;

import java.util.List;
import kr.co.yigil.member.domain.MemberInfo;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseReaderImpl implements CourseReader {
    private final CourseRepository courseRepository;

    @Override
    public MemberInfo.MemberCourseResponse findAllByMemberId(Long memberId, Pageable pageable, String selectedInfo) {
        Page<Course> pageMember;
        if (selectedInfo.equals("all")) {
            pageMember = courseRepository.findAllByMemberId(memberId, pageable);
        } else {
            pageMember = courseRepository.findAllByMemberIdAndIsPrivate(memberId, selectedInfo.equals("private"), pageable);
        }
        pageMember = courseRepository.findAllByMemberId(memberId, pageable);
        List<MemberInfo.CourseInfo> courseInfoList =  pageMember.getContent().stream()
            .map(MemberInfo.CourseInfo::new)
            .toList();
        return new MemberInfo.MemberCourseResponse(courseInfoList, pageMember.getTotalPages());
    }
}
