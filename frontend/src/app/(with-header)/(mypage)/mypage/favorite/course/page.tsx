import { getFavoriteCourses } from '@/app/_components/mypage/favorite/FavoriteActions';
import FavoriteCourseList from '@/app/_components/mypage/favorite/FavoriteCourseList';
import React from 'react';

export default async function FavoriteCoursePage() {
  const res = await getFavoriteCourses();

  if (res.status === 'failed') throw new Error(res.message);

  return (
    <>
      {!!res.data.contents.length ? (
        <FavoriteCourseList
          favoriteCourseList={res.data.contents}
          has_next={res.data.has_next}
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          좋아요 한 코스가 없습니다.
        </div>
      )}
    </>
  );
}
