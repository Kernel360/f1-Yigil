import MyPageCourseList from '@/app/_components/mypage/course/MyPageCourseList';
import React from 'react';
//
const url =
  process.env.NODE_ENV === 'production'
    ? process.env.BASE_URL
    : 'http://localhost:8080/api/v1';
export default async function MyPageMyCourse() {
  const res = await fetch(`${url}/member/course`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  const result = await res.json();

  return (
    <>
      <MyPageCourseList placeList={result.data} />
    </>
  );
}
