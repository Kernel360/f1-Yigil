import { getFollowerList } from '@/app/_components/mypage/hooks/followActions';
import MyPageFollowingList from '@/app/_components/mypage/following/myPageFollowingList';
import React from 'react';
import MyPageFollowerList from '@/app/_components/mypage/follower/MyPageFollowerList';

export default async function FollowerPage() {
  const followList = await getFollowerList();
  if (!followList.success) return <div>failed</div>;
  return (
    <>
      {!!followList.data.content.length ? (
        <MyPageFollowerList followerList={followList.data.content} />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          팔로우 된 유저가 없습니다.
        </div>
      )}
    </>
  );
}
