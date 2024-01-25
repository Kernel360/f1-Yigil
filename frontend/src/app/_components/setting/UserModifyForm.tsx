'use client';
import { Session } from 'next-auth';
import React, { useState } from 'react';
import SettingProfile from './SettingProfile';
import SettingUserForm from './SettingUserForm';

export interface UserType {
  name?: string | null;
  email?: string | null;
  image?: string | null;
  gender?: string;
  age?: string;
  area?: string[];
}

export default function UserModifyForm({
  session,
}: {
  session: Session | null;
}) {
  const [userForm, setUserForm] = useState<UserType | undefined>({
    ...session?.user,
    gender: '',
    age: '',
    area: [],
  });

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
