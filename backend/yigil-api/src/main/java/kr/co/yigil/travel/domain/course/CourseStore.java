package kr.co.yigil.travel.domain.course;

import kr.co.yigil.travel.domain.Course;

public interface CourseStore {
    Course store(Course initStore);
    void remove(Course course);
}