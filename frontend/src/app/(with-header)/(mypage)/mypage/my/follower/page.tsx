import { getFollowerList } from '@/app/_components/mypage/hooks/followActions';
import React from 'react';
import MyPageFollowerList from '@/app/_components/mypage/follower/MyPageFollowerList';

export default async function FollowerPage() {
  const followerList = await getFollowerList();

  if (followerList.status === 'failed') throw new Error(followerList.message);
  return (
    <>
      {!!followerList.data.content.length ? (
        <MyPageFollowerList followerList={followerList.data.content} />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          팔로우 된 유저가 없습니다.
        </div>
      )}
    </>
  );
}
