import MyPageFollowingList from '@/app/_components/mypage/following/MyPageFollowingList';
import { getFollowingList } from '@/app/_components/mypage/hooks/followActions';

import React from 'react';

export default async function FollowingPage() {
  const followingList = await getFollowingList();
  if (followingList.status === 'failed') throw new Error(followingList.message);
  return (
    <>
      {!!followingList.data.content.length ? (
        <MyPageFollowingList followingList={followingList.data.content} />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          팔로우한 유저가 없습니다.
        </div>
      )}
    </>
  );
}
