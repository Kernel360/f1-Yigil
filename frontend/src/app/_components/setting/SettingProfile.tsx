'use client';
import { Session } from 'next-auth';
import React, { Dispatch, SetStateAction, useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import { TModifyUser } from './ModifyUser';
import { EventFor } from '@/types/type';

export default function SettingProfile({
  session,
  userForm,
  setUserForm,
}: {
  session: Session | null;
  userForm: TModifyUser | undefined;
  setUserForm: Dispatch<SetStateAction<TModifyUser | undefined>>;
}) {
  const onChangeImg = (e: EventFor<'input', 'onChange'>) => {
    if (!e.target.files?.length) return;
    const imageFile = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(imageFile);
    reader.onload = function (e: ProgressEvent<FileReader>) {
      if (!e || !e.target) return;
      if (typeof e.target.result !== 'string') return;
      setUserForm({
        ...userForm,
        image: e.target.result,
        postImageFile: imageFile,
      });
    };
  };

  return (
    <div>
      <div className="flex justify-center items-center">
        <label htmlFor="file-upload" className="rounded-full">
          <RoundProfile
            img={userForm?.image || ''}
            size={192}
            height="h-[192px]"
            style="cursor-pointer"
          />
          <input
            type="file"
            className="hidden"
            id="file-upload"
            onChange={onChangeImg}
          />
        </label>
      </div>
    </div>
  );
}
