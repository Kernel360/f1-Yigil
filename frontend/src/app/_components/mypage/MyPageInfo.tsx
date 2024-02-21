'use client';
import { useSession } from 'next-auth/react';
import React from 'react';
import RoundProfile from '../ui/profile/RoundProfile';

export default function MyPageInfo({
  memberInfo,
}: {
  memberInfo: {
    nickname: string;
    profile_image_url: string;
    follower_count: number;
    following_count: number;
  };
}) {
  const { nickname, profile_image_url, follower_count, following_count } =
    memberInfo;

  return (
    <section
      className="py-3 pl-4 flex items-center gap-x-4"
      aria-label="profile"
    >
      <RoundProfile
        img={profile_image_url}
        size={40}
        height="w-[80px] h-[80px]"
      />
      <div className="flex flex-col gap-y-3">
        <div className="text-2xl text-gray-900">{nickname}</div>
        <div className="flex items-center">
          <div className="text-gray-500 mr-2">팔로워</div>
          <div className="mr-4">{follower_count}</div>
          <div className="text-gray-500 mr-2">팔로잉</div>
          <div>{following_count}</div>
        </div>
        {/* <div className="text-gray-500">{data?.user?.email}</div> */}
      </div>
    </section>
  );
}
