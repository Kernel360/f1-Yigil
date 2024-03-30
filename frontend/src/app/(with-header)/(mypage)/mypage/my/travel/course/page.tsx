import MyPageCourseList from '@/app/_components/mypage/course/MyPageCourseList';
import { getMyPageCourses } from '@/app/_components/mypage/hooks/myPageActions';
import React from 'react';

export default async function MyPageMyCourse() {
  const courseList = await getMyPageCourses();

  if (courseList.status === 'failed') throw new Error(courseList.message);

  return (
    <>
      {!!courseList.data.content.length ? (
        <MyPageCourseList
          placeList={courseList.data.content}
          totalPage={courseList.data.total_pages}
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          코스를 추가해주세요.
        </div>
      )}
    </>
  );
}
