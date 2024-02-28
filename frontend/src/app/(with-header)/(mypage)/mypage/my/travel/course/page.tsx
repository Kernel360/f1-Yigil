import MyPageCourseList from '@/app/_components/mypage/course/MyPageCourseList';
import { getMyPageCourses } from '@/app/_components/mypage/hooks/myPageActions';
import { myPageCourseListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageMyCourse() {
  const courseList = await getMyPageCourses();
  const parsedCourseList = myPageCourseListSchema.safeParse(courseList.content);
  if (!parsedCourseList.success) return <div>failed</div>;
  return (
    <>
      {!!courseList.content.length ? (
        <MyPageCourseList
          placeList={courseList.content}
          totalPage={courseList.totalPage}
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          코스를 추가해주세요.
        </div>
      )}
    </>
  );
}
