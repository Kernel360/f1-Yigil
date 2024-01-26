import UserModifyForm from '@/app/_components/setting/ModifyUser';
import { getServerSession } from 'next-auth';
import React from 'react';

export default async function SettingPage() {
  const session = await getServerSession();

  return (
    <div className="flex flex-col">
      <UserModifyForm session={session} />
    </div>
  );
}
