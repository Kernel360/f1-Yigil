'use client';
import { useSession } from 'next-auth/react';
import React from 'react';
import RoundProfile from '../ui/profile/RoundProfile';

export default function MyPageInfo() {
  const { data } = useSession();

  return (
    <section
      className="py-3 pl-4 flex items-center border-b-[1px] gap-x-4"
      aria-label="profile"
    >
      <RoundProfile
        img={data?.user?.image as string}
        size={40}
        height="w-[80px] h-[80px]"
      />
      <div className="flex flex-col gap-y-3">
        <div className="text-2xl text-gray-900">{data?.user?.name}</div>
        <div className="flex items-center">
          <div className="text-gray-500 mr-2">팔로워</div>
          <div className="mr-4">100</div>
          <div className="text-gray-500 mr-2">팔로잉</div>
          <div>100</div>
        </div>
        {/* <div className="text-gray-500">{data?.user?.email}</div> */}
      </div>
    </section>
  );
}
