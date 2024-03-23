package kr.co.yigil.travel.course.application;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.travel.course.domain.CourseInfoDto;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CourseDetailInfo;
import kr.co.yigil.travel.course.domain.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseFacade {

    private final CourseService courseService;
    private final AdminService adminService;
    private final NotificationService notificationService;

    public CourseInfoDto.CoursesPageInfo getCourses(PageRequest pageRequest) {
        return courseService.getCourses(pageRequest);
    }

    public CourseDetailInfo getCourse(Long courseId) {
        return courseService.getCourse(courseId);
    }

    public void deleteCourse(Long courseId) {
        Long memberId = courseService.deleteCourse(courseId);
        Long adminId = adminService.getAdminId();
        notificationService.saveNotification(NotificationType.COURSE_DELETED, adminId, memberId);
    }


}
