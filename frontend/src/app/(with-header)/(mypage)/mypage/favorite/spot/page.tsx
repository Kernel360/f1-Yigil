import { getFavoriteSpots } from '@/app/_components/mypage/favorite/FavoriteActions';
import FavoriteSpotList from '@/app/_components/mypage/favorite/FavoriteSpotList';
import React from 'react';

export default async function FavoriteSpotPage() {
  const res = await getFavoriteSpots();

  if (res.status === 'failed') {
    return (
      <div className="w-full h-full flex flex-col break-words justify-center items-center text-3xl text-center text-main">
        장소를 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
      </div>
    );
  }
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
