'use client';
import { useSession } from 'next-auth/react';
import React from 'react';
import RoundProfile from '../ui/profile/RoundProfile';

export default function MyPageInfo() {
  const { data } = useSession();
  return (
    <div className="h-[72px] pl-4 flex items-center border-b-[1px] gap-x-4">
      <RoundProfile
        img={data?.user?.image as string}
        size={40}
        height="h-[40px]"
      />
      <div className="flex flex-col">
        <div className="text-gray-900">{data?.user?.name}</div>
        <div className="text-gray-500">{data?.user?.email}</div>
      </div>
    </div>
  );
}
