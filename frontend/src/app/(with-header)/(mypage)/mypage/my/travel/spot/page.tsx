import { getMyPageSpots } from '@/app/_components/mypage/hooks/myPageActions';
import MyPageSpotList from '@/app/_components/mypage/spot/MyPageSpotList';
import { myPageSpotListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageMySpot() {
  const spotList = await getMyPageSpots();
  const parsedSpotList = myPageSpotListSchema.safeParse(spotList.content);
  if (!parsedSpotList.success) return <div>failed</div>;

  return (
    <MyPageSpotList
      placeList={parsedSpotList.data}
      totalPage={spotList.total_page}
    />
  );
}
