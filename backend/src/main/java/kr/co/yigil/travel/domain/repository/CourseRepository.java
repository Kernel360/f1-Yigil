package kr.co.yigil.travel.domain.repository;

import kr.co.yigil.travel.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
