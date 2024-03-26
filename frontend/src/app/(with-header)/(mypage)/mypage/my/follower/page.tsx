import { getFollowList } from '@/app/_components/mypage/hooks/followActions';
import React from 'react';

import MyPageFollowList from '@/app/_components/mypage/follow/MyPageFollowList';

export default async function FollowerPage() {
  const followList = await getFollowList(1, 5, 'id', 'followers');

  if (followList.status === 'failed') throw new Error(followList.message);
  return (
    <>
      {!!followList.data.content.length ? (
        <MyPageFollowList
          followList={followList.data.content}
          action="followers"
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          팔로우 된 유저가 없습니다.
        </div>
      )}
    </>
  );
}
