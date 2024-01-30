'use client';
import { useSession } from 'next-auth/react';
import React from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
/**
 * TODO: getServerSession promise 에러 해결하기
 */
export default function MyPageInfo() {
  const { data } = useSession();
  return (
    <div className="py-3 pl-4 flex items-center border-b-[1px] gap-x-4">
      <RoundProfile
        img={data?.user?.image as string}
        size={40}
        height="w-[48px] h-[48px]"
      />
      <div className="flex flex-col">
        <div className="text-gray-900">{data?.user?.name}</div>
        <div className="text-gray-500">{data?.user?.email}</div>
      </div>
    </div>
  );
}
