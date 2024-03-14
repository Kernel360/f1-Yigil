'use client';
import React, { Dispatch, SetStateAction, useRef, useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import { EventFor } from '@/types/type';
import { blobTodataUrl } from '@/utils';
import { TMyInfo } from '@/types/response';
import PlusIcon from '/public/icons/plus.svg';
export default function SettingProfile({
  userForm,
  setUserForm,
}: {
  userForm: TMyInfo;
  setUserForm: Dispatch<SetStateAction<TMyInfo>>;
}) {
  const [isIconClicked, setIconClicked] = useState(false);
  const [isPopoverOpened, setPopoverOpened] = useState(false);
  const onChangeImg = async (e: EventFor<'input', 'onChange'>) => {
    if (!e.target.files?.length) return;
    const imageFile = e.target.files[0];
    const imageUrl = await blobTodataUrl(imageFile);
    setUserForm({ ...userForm, profile_image_url: imageUrl });
    closePopOver();
  };

  const inputRef = useRef<HTMLInputElement>(null);

  const openChangeImage = () => {
    if (inputRef.current) inputRef.current.click();
  };

  const changeDefaultImage = () => {
    setUserForm({
      ...userForm,
      profile_image_url: '',
    });
    closePopOver();
  };

  const popOverData = [
    {
      label: '앨범에서 사진 선택',
      onClick: () => openChangeImage(),
    },
    {
      label: '기본 이미지로 변경',
      onClick: () => changeDefaultImage(),
    },
  ];

  const onClickProfileChangeIcon = () => {
    setIconClicked((prev) => !prev);
    if (isIconClicked) {
      setPopoverOpened(false);
    } else {
      setPopoverOpened(true);
    }
  };
  const closePopOver = () => {
    setPopoverOpened(false);
  };

  return (
    <div>
      <div className="flex justify-center items-center ">
        <div className="rounded-full relative">
          <RoundProfile
            img={userForm?.profile_image_url || ''}
            size={192}
            height="h-[192px]"
            style="cursor-pointer"
          />
          <input
            type="file"
            className="hidden"
            id="file-upload"
            onChange={onChangeImg}
            ref={inputRef}
          />
          <div
            className="absolute rounded-full p-2 bg-black border-white border-2 bottom-0 right-0 cursor-pointer"
            onClick={onClickProfileChangeIcon}
          >
            <PlusIcon
              className={`w-10 ${isIconClicked ? 'rotate-45' : 'rotate-0'}`}
            />
            {isPopoverOpened && (
              <div className="absolute top-[-100px] left-[-30px] bg-[#F3F4F6] rounded-md flex flex-col items-center justify-center">
                <ul className="flex flex-col gap-2 justify-center items-center shrink-0">
                  {popOverData.map(({ label, onClick }) => (
                    <div
                      key={label}
                      onClick={onClick}
                      className="break-keep whitespace-nowrap py-2 px-4"
                    >
                      {label}
                    </div>
                  ))}
                </ul>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
