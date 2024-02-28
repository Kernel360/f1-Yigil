import { getMyPageSpots } from '@/app/_components/mypage/hooks/myPageActions';
import MyPageSpotList from '@/app/_components/mypage/spot/MyPageSpotList';
import { myPageSpotListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageMySpot() {
  const spotList = await getMyPageSpots();
  const parsedSpotList = myPageSpotListSchema.safeParse(spotList.content);
  if (!parsedSpotList.success) return <div>failed</div>;
  console.log(spotList);

  return (
    <>
      <MyPageSpotList
        placeList={spotList.content}
        totalPage={spotList.total_page}
      />
      {/* {!!spotList.content.length ? (
        <MyPageSpotList
          placeList={spotList.content}
          totalPage={spotList.total_page}
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          장소를 추가해주세요.
        </div>
      )} */}
    </>
  );
}
