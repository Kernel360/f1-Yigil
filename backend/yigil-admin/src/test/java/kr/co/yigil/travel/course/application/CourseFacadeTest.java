package kr.co.yigil.travel.course.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.travel.course.domain.CourseInfoDto;
import kr.co.yigil.travel.course.domain.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;


@ExtendWith(MockitoExtension.class)
class CourseFacadeTest {

    @Mock
    private CourseService courseService;
    @Mock
    private AdminService adminService;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private CourseFacade courseFacade;

    @DisplayName("getCourses 메서드가 CourseService를 잘 호출하는지")
    @Test
    void getCourses() {
        when(courseService.getCourses(any(PageRequest.class))).thenReturn(
            mock(CourseInfoDto.CoursesPageInfo.class));
        var response = courseFacade.getCourses(PageRequest.of(0, 10));

        assertThat(response).isInstanceOf(CourseInfoDto.CoursesPageInfo.class);
    }

    @Test
    void getCourse() {
        when(courseService.getCourse(anyLong())).thenReturn(
            mock(CourseInfoDto.CourseDetailInfo.class));
        var response = courseFacade.getCourse(1L);
        assertThat(response).isInstanceOf(CourseInfoDto.CourseDetailInfo.class);
    }

    @Test
    void deleteCourse() {
        Long adminId = 1L;
        Long memberId = 5L;
        when(courseService.deleteCourse(anyLong())).thenReturn(memberId);
        when(adminService.getAdminId()).thenReturn(adminId);
        courseFacade.deleteCourse(1L);
        verify(notificationService).saveNotification(NotificationType.COURSE_DELETED, adminId, memberId);
    }
}