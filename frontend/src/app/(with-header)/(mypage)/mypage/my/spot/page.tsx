import { myPageSpotRequest } from '@/app/_components/mypage/hooks/useMyPage';

import MyPageSpotList, {
  TMyPageSpot,
} from '@/app/_components/mypage/spot/MyPageSpotList';
import React from 'react';

// export default async function MyPageMySpot() {
// const res: TMyPageSpot[] = await myPageSpotRequest(
//   '?page=1&size=5&sortOrder=desc',
// )()()();
// console.log(res);
const res: any[] = [];
const url =
  process.env.NODE_ENV === 'production'
    ? process.env.BASE_URL
    : 'http://localhost:8080/api/v1';
export default async function MyPageMySpot() {
  const result = await fetch(`${url}/member/spot`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  const res = await result.json();

  return (
    <>
      {/* <MyPageSpotList placeList={res ? res : []} /> */}
      <MyPageSpotList placeList={res ? res.data : []} />
    </>
  );
}
