import { getFollowingList } from '@/app/_components/mypage/following/actions';
import MyPageFollowingList from '@/app/_components/mypage/following/myPageFollowingList';
import React from 'react';

export default async function FollowingPage() {
  const followList = await getFollowingList();
  if (!followList.success) return <div>failed</div>;

  return (
    <>
      <MyPageFollowingList followingList={followList.data.content} />
    </>
  );
}
