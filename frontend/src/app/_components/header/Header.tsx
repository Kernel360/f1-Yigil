import React from 'react';
import HeaderClient from './HeaderClient';
import { Session } from 'next-auth';
import { TUserInfo } from '../mypage/types';

export default function Header({
  memberInfo,
}: {
  memberInfo: TUserInfo | null;
}) {
  return (
    <div className="w-full py-4 flex justify-between items-center sticky top-0 bg-main z-10">
      <HeaderClient memberInfo={memberInfo} />
    </div>
  );
}
