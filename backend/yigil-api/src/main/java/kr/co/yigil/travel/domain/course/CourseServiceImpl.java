package kr.co.yigil.travel.domain.course;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final MemberReader memberReader;
    private final CourseReader courseReader;

    private final CourseStore courseStore;

    private final CourseSpotSeriesFactory courseSpotSeriesFactory;

    @Override
    public Slice<Course> getCoursesSliceInPlace(Long placeId, Pageable pageable) {
        return courseReader.getCoursesSliceInPlace(placeId, pageable);
    }

    @Override
    @Transactional
    public void registerCourse(RegisterCourseRequest command, Long memberId) {
        Member member = memberReader.getMember(memberId);
        var spots = courseSpotSeriesFactory.store(command, memberId);
        var initCourse = command.toEntity(spots, member);
        var course = courseStore.store(initCourse);
    }
}
