import { getFavoriteSpots } from '@/app/_components/mypage/favorite/FavoriteActions';
import FavoriteSpotList from '@/app/_components/mypage/favorite/FavoriteSpotList';
import React from 'react';

export default async function FavoriteSpotPage() {
  const res = await getFavoriteSpots();

  if (res.status === 'failed') throw new Error(res.message);

  return (
    <>
      {!!res.data.contents.length ? (
        <FavoriteSpotList
          favoriteSpotList={res.data.contents}
          has_next={res.data.has_next}
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          좋아요 한 장소가 없습니다.
        </div>
      )}
    </>
  );
}
