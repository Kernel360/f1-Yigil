'use client';
import { Session } from 'next-auth';
import React, { useState } from 'react';
import SettingProfile from './SettingProfile';
import SettingUserForm from './SettingUserForm';

export interface TModifyUser {
  name?: string | null;
  email?: string | null;
  image?: string | null;
  postImageFile?: File;
  gender?: string;
  age?: string;
  area?: string[];
}

export default function UserModifyForm({
  session,
}: {
  session: Session | null;
}) {
  const [userForm, setUserForm] = useState<TModifyUser | undefined>({
    ...session?.user,
    gender: '',
    age: '',
    area: [],
  });
  // 디자인 나오면 api 요청 보내는 로직

  return (
    <>
      <div className="my-2">
        <SettingProfile
          session={session}
          userForm={userForm}
          setUserForm={setUserForm}
        />
      </div>
      <div className="mt-7">
        <SettingUserForm userForm={userForm} setUserForm={setUserForm} />
      </div>
    </>
  );
}
