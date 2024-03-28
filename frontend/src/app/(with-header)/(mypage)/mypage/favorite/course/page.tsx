import { getFavoriteCourses } from '@/app/_components/mypage/favorite/FavoriteActions';
import FavoriteCourseList from '@/app/_components/mypage/favorite/FavoriteCourseList';
import React from 'react';

export default async function FavoriteCoursePage() {
  const res = await getFavoriteCourses();

  if (res.status === 'failed') {
    // throw로 변경해야함
    return (
      <div className="w-full h-full flex flex-col break-words justify-center items-center text-3xl text-center text-main">
        코스를 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
      </div>
    );
  }
  return (
    <>
      <FavoriteCourseList
        favoriteCourseList={res.data.contents}
        has_next={res.data.has_next}
      />
    </>
  );
}
