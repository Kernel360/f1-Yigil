'use client';

import React, { useEffect, useState } from 'react';
import SettingProfile from './SettingProfile';
import SettingUserForm from './SettingUserForm';
import { TMyInfo } from '@/types/response';
import Dialog from '../ui/dialog/Dialog';
import ToastMsg from '../ui/toast/ToastMsg';
import { patchUserInfo } from './actions';

export default function UserModifyForm(userData: TMyInfo) {
  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const [toastMsg, setToastText] = useState('');
  const [userForm, setUserForm] = useState<TMyInfo>({
    ...userData,
    email: '',
    member_id: 0,
  });

  const patchUserForm = async () => {
    const patchData = checkDifference(userForm, userData);
    const res = await patchUserInfo(patchData);
    if (res.status === 'failed') {
      setToastText('수정에 실패했습니다.');
      setTimeout(() => {
        setToastText('');
      }, 2000);
    }
    setToastText('수정 완료되었습니다.');
    setTimeout(() => {
      setToastText('');
    }, 2000);

    setIsDialogOpened(false);
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
      {toastMsg && <ToastMsg title={toastMsg} timer={2000} />}
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
        : '변경 없음',
    ages:
      userForm.ages === userData.ages || userForm.ages === '없음'
        ? ''
        : userForm.ages,
    gender:
      userForm.gender === userData.gender || userForm.gender === '없음'
        ? ''
        : userForm.gender,
    favorite_regions: '',
  };
  for (const key in patchData) {
    if (
      !patchData[key] ||
      patchData[key] === '없음' ||
      key === 'favorite_regions'
    ) {
      delete patchData[key];
    }
    if (key === 'profile_image_url' && !patchData[key]) patchData[key] = '';
  }
  return patchData;
}
