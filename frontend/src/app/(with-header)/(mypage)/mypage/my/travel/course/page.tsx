import MyPageCourseList from '@/app/_components/mypage/course/MyPageCourseList';
import { getMyPageCourses } from '@/app/_components/mypage/hooks/myPageActions';
import React from 'react';

export default async function MyPageMyCourse() {
  const courseList = await getMyPageCourses();
  if (!courseList.success) return <div>failed</div>;
  return (
    <>
      {!!courseList.data.content.length ? (
        <MyPageCourseList
          placeList={courseList.data.content}
          totalPage={courseList.data.total_pages}
        />
      ) : (
        <section className="grow flex flex-col justify-center items-center gap-8">
          <span className="text-6xl">🚧</span>
          <br />
          <span className="text-5xl">준비 중입니다!</span>
        </section>
      )}
    </>
  );
}
