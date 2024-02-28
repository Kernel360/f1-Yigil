import { getMyPageSpots } from '@/app/_components/mypage/spot/spotActions';
import MyPageSpotList from '@/app/_components/mypage/spot/MyPageSpotList';
import { myPageSpotListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageMySpot() {
  const spotList = await getMyPageSpots();
  const parsedSpotList = myPageSpotListSchema.safeParse(spotList.content);

  if (!parsedSpotList.success) return <div>failed</div>;

  return (
    <>
      <MyPageSpotList
        placeList={spotList.content}
        totalPage={spotList.total_page}
      />
    </>
  );
}
