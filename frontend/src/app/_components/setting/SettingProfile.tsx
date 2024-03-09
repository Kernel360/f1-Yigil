'use client';
import React, { Dispatch, SetStateAction, useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import { TModifyUser } from './ModifyUser';
import { EventFor } from '@/types/type';
import { blobTodataUrl } from '@/utils';

export default function SettingProfile({
  userForm,
  setUserForm,
}: {
  userForm: TModifyUser;
  setUserForm: Dispatch<SetStateAction<TModifyUser>>;
}) {
  const onChangeImg = async (e: EventFor<'input', 'onChange'>) => {
    if (!e.target.files?.length) return;
    const imageFile = e.target.files[0];
    const imageUrl = await blobTodataUrl(imageFile);
    setUserForm({ ...userForm, profile_image_url: imageUrl });
  };

  return (
    <div>
      <div className="flex justify-center items-center">
        <label htmlFor="file-upload" className="rounded-full">
          <RoundProfile
            img={(userForm?.profile_image_url as string) || ''}
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
