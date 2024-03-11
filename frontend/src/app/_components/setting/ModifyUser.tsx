'use client';

import React, { useEffect, useState } from 'react';
import SettingProfile from './SettingProfile';
import SettingUserForm from './SettingUserForm';
import { TMyInfo } from '@/types/response';
import { patchUserInfo } from './actions';
import Dialog from '../ui/dialog/Dialog';
import ToastMsg from '../ui/toast/ToastMsg';

export default function UserModifyForm(userData: TMyInfo) {
  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [userForm, setUserForm] = useState<TMyInfo>({
    ...userData,
    email: '',
    member_id: 0,
  });

  const patchUserForm = async () => {
    try {
      const patchData = checkDifference(userForm, userData);
      await patchUserInfo(patchData);
    } catch (error) {
      setErrorText('수정에 실패했습니다.');
      console.log(error);
    } finally {
      setIsDialogOpened(false);
    }
  };

  const closeModal = () => {
    setIsDialogOpened(false);
  };

  const openModal = () => {
    setIsDialogOpened(true);
  };

  useEffect(() => {
    setUserForm({ ...userData, email: '', member_id: 0 });
  }, [userData]);

  return (
    <>
      <div className="my-2">
        <SettingProfile userForm={userForm} setUserForm={setUserForm} />
      </div>
      <div className="mt-7">
        <SettingUserForm
          fetchUserData={userData}
          userForm={userForm}
          setUserForm={setUserForm}
          openModal={openModal}
        />
      </div>
      {isDialogOpened && (
        <Dialog
          text="수정하시겠습니까?"
          closeModal={closeModal}
          handleConfirm={async () => await patchUserForm()}
          loadingText="수정중 입니다."
        />
      )}
      {errorText && <ToastMsg title={errorText} timer={2000} />}
    </>
  );
}

function checkDifference(userForm: TMyInfo, userData: TMyInfo) {
  const patchData: { [key: string]: {} } = {
    ...userForm,
    nickname: userForm.nickname !== userData.nickname ? userForm.nickname : '',
    profile_image_url:
      userForm.profile_image_url !== userData.profile_image_url
        ? userForm.profile_image_url
        : '',
    age: userForm.age === '없음' ? '' : userForm.age,
    gender: userForm.gender === '없음' ? '' : userForm.gender,
    favorite_regions: '',
  };
  for (let key in patchData) {
    if (
      !patchData[key] ||
      patchData[key] === '없음' ||
      key === 'favorite_regions'
    ) {
      delete patchData[key];
    }
  }
  return patchData;
}
