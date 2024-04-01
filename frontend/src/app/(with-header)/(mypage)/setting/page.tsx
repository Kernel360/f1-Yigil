import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import UserModifyForm from '@/app/_components/setting/ModifyUser';

import { myInfoSchema } from '@/types/response';

import React from 'react';

export default async function SettingPage() {
  const res = await authenticateUser();
  const user = myInfoSchema.safeParse(res);
  if (!user.success) throw new Error(user.error.message);

  return (
    <div className="flex flex-col">
      <UserModifyForm {...user.data} />
    </div>
  );
}
