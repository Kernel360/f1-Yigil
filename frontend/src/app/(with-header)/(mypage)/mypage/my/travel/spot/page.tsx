import { getMyPageSpots } from '@/app/_components/mypage/hooks/myPageActions';
import MyPageSpotList from '@/app/_components/mypage/spot/MyPageSpotList';
import ToastMsg from '@/app/_components/ui/toast/ToastMsg';

import React from 'react';

export default async function MyPageMySpot() {
  const spotList = await getMyPageSpots();
  if (!spotList.success)
    return (
      <>
        <div className="w-full h-full flex flex-col break-words justify-center items-center text-3xl text-center text-main">
          장소를 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
        </div>
        <ToastMsg title="장소를 불러오는데 실패했습니다" timer={2000} />
      </>
    );

  return (
    <>
      {!!spotList.data.content.length ? (
        <MyPageSpotList
          placeList={spotList.data.content}
          totalPage={spotList.data.total_pages}
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          장소를 추가해주세요.
        </div>
      )}
    </>
  );
}
