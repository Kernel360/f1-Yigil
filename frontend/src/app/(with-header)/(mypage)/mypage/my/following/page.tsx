import { getFollowingList } from '@/app/_components/mypage/hooks/followActions';
import MyPageFollowingList from '@/app/_components/mypage/following/myPageFollowingList';
import React from 'react';

export default async function FollowingPage() {
  const followList = await getFollowingList();
  if (!followList.success) return <div>failed</div>;
  return (
    <>
      {!!followList.data.content.length ? (
        <MyPageFollowingList followingList={followList.data.content} />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          팔로우한 유저가 없습니다.
        </div>
      )}
    </>
  );
}
