package kr.co.yigil.travel.repository;

import kr.co.yigil.travel.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}