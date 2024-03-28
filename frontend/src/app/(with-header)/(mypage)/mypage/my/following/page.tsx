import MyPageFollowList from '@/app/_components/mypage/follow/MyPageFollowList';
import { getFollowList } from '@/app/_components/mypage/hooks/followActions';

import React from 'react';

export default async function FollowingPage() {
  const followList = await getFollowList(1, 5, 'id', 'followings');
  if (followList.status === 'failed') throw new Error(followList.message);
  return (
    <>
      {!!followList.data.content.length ? (
        <MyPageFollowList
          followList={followList.data.content}
          action="followings"
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          팔로우한 유저가 없습니다.
        </div>
      )}
    </>
  );
}
