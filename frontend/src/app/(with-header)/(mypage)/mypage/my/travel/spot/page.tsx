import { getMyPageSpots } from '@/app/_components/mypage/hooks/myPageActions';
import MyPageSpotList from '@/app/_components/mypage/spot/MyPageSpotList';
import React from 'react';

export default async function MyPageMySpot() {
  const spotList = await getMyPageSpots();

  return (
    <>
      <MyPageSpotList
        placeList={spotList.data}
        totalCount={spotList.totalCount}
      />
    </>
  );
}
