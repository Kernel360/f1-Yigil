'use client';
import React, { Dispatch, SetStateAction } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import { EventFor } from '@/types/type';
import { blobTodataUrl } from '@/utils';
import { TMyInfo } from '@/types/response';

export default function SettingProfile({
  userForm,
  setUserForm,
}: {
  userForm: TMyInfo;
  setUserForm: Dispatch<SetStateAction<TMyInfo>>;
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
