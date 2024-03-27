import React from 'react';
import RoundProfile from '../ui/profile/RoundProfile';

export default function MyPageInfo({
  memberInfo,
}: {
  memberInfo: {
    nickname: string;
    email: string;
    profile_image_url: string;
    follower_count: number;
    following_count: number;
  };
}) {
  return (
    <section
      className="py-3 pl-4 flex items-center gap-x-4"
      aria-label="profile"
    >
      <RoundProfile
        img={memberInfo?.profile_image_url}
        size={40}
        height="w-[80px] h-[80px]"
      />
      <div className="flex flex-col gap-y-1">
        <div className="text-2xl text-gray-900">{memberInfo?.nickname}</div>
        <div className="text-md text-gray-900">{memberInfo?.email}</div>
        <div className="flex items-center">
          <div className="text-gray-500 mr-2">팔로워</div>
          <div className="mr-4">{memberInfo?.follower_count}</div>
          <div className="text-gray-500 mr-2">팔로잉</div>
          <div>{memberInfo?.following_count}</div>
        </div>
      </div>
    </section>
  );
}

/**
 *
 * TODO: 마이페이지 들어갔을 때 유저 검증 (memberInfo의 코드에 따라)
 * */
