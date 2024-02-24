import MyPageCourseList from '@/app/_components/mypage/course/MyPageCourseList';
import { getMyPageCourses } from '@/app/_components/mypage/hooks/myPageActions';
import React from 'react';

export default async function MyPageMyCourse() {
  const courseList = await getMyPageCourses();
  console.log(courseList);
  return (
    <>
      <MyPageCourseList
        placeList={courseList.content}
        totalPage={courseList.totalPage}
      />
    </>
  );
}
