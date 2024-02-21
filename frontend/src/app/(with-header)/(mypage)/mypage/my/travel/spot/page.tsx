import MyPageSpotList, {
  TMyPageSpot,
} from '@/app/_components/mypage/spot/MyPageSpotList';
import React from 'react';

export default async function MyPageMySpot() {
  const result = await fetch(
    `http://localhost:8080/api/v1/members/spot?page=1&size=5&sortOrder=desc&selected=all`,

    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    },
  );

  const res = await result.json();

  return (
    <>
      {/* <MyPageSpotList placeList={res ? res : []} /> */}
      <MyPageSpotList placeList={res.data} />
    </>
  );
}
