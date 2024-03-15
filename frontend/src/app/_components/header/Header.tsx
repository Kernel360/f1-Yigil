import React from 'react';
import HeaderClient from './HeaderClient';
import { authenticateUser } from '../mypage/hooks/authenticateUser';

export default async function Header() {
  const user = await authenticateUser();

  return (
    <div className="w-full py-4 flex justify-between items-center sticky top-0 bg-main z-10">
      <HeaderClient user={user.code ? undefined : user} />
    </div>
  );
}
