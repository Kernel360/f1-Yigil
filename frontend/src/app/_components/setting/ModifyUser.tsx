'use client';

import React, { useState } from 'react';
import SettingProfile from './SettingProfile';
import SettingUserForm from './SettingUserForm';
import { TMyInfo } from '@/types/response';
import { dataUrlToBlob } from '@/utils';
import { patchUserInfo } from './actions';
import LoadingIndicator from '../LoadingIndicator';
import ViewPortal from '../Portal';
import Dialog from '../ui/dialog/Dialog';

export interface TModifyUser {
  nickname: string;
  profile_image_url: string;
  favorite_regions: { id: number; name: string }[];
  gender: string;
  age: string;
  a?: string[];
}

export default function UserModifyForm(userData: TMyInfo) {
  const [isLoading, setIsLoading] = useState(false);
  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const [userForm, setUserForm] = useState<TModifyUser>({
    ...userData,
    // 타입 수정 되어야 함
    favorite_regions: userData.favorite_regions_ids,
    gender: '',
    age: '',
  });
  // 디자인 나오면 api 요청 보내는 로직
  const onClickComplete = async () => {
    try {
      setIsLoading(true);
      // await patchUserInfo(userForm);
    } catch (error) {
      console.log(error);
    } finally {
      setIsLoading(false);
    }
  };

  const closeModal = () => {
    setIsDialogOpened(false);
  };

  const openModal = () => {
    setIsDialogOpened(true);
  };

  return (
    <>
      <div className="my-2">
        <SettingProfile userForm={userForm} setUserForm={setUserForm} />
      </div>
      <div className="mt-7">
        <SettingUserForm
          userForm={userForm}
          setUserForm={setUserForm}
          openModal={openModal}
        />
      </div>
      {isDialogOpened && (
        <Dialog
          text="수정하시겠습니까?"
          closeModal={closeModal}
          handleConfirm={async () => await onClickComplete()}
        />
      )}
    </>
  );
}
