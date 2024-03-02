import MyPageCourseList from '@/app/_components/mypage/course/MyPageCourseList';
import { getMyPageCourses } from '@/app/_components/mypage/hooks/myPageActions';
import { myPageCourseListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageMyCourse() {
  const courseList = await getMyPageCourses();
  if (!courseList.success) return <div>failed</div>;
  return (
    <>
      <MyPageCourseList
        placeList={courseList.data.content}
        totalPage={courseList.data.total_pages}
      />
    </>
  );
}
