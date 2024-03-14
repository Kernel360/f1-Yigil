'use client';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import HeaderLogo from '/public/logo/yigil_logo.svg';
import Link from 'next/link';
import { EventFor } from '@/types/type';
import PopOver from '../ui/popover/PopOver';
import { headerPopOverData } from '../ui/popover/constants';
import { TMyInfo } from '@/types/response';

export default function HeaderClient({ user }: { user: TMyInfo }) {
  const router = useRouter();
  const [isModalOpened, setIsModalOpened] = useState(false);

  const onKeyDown = (e: EventFor<'span', 'onKeyDown'>) => {
    if (e.key === 'Enter') setIsModalOpened(true);
    else if (e.key === 'Escape') setIsModalOpened(false);
  };

  const closeModal = () => {
    setIsModalOpened(false);
  };

  return (
    <>
      <div className="ml-4 cursor-pointer" onClick={() => router.push('/')}>
        <HeaderLogo className="w-[145px] h-[48px]" />
      </div>

      {user ? (
        <>
          <span
            className="mr-4"
            onClick={() => setIsModalOpened(true)}
            onKeyDown={onKeyDown}
            tabIndex={0}
          >
            <RoundProfile
              img={user.profile_image_url as string}
              size={40}
              style="cursor-pointer"
              height="h-[40px]"
            />
          </span>
          {isModalOpened && (
            <PopOver
              popOverData={headerPopOverData}
              closeModal={closeModal}
              position="top-16 right-4"
              style="z-30"
            />
          )}
        </>
      ) : (
        <Link
          href="/login"
          className="flex justify-center items-center w-[68px] h-10 mr-4 border-[1px] border-white border-solid rounded-md text-white text-xl no-underline"
        >
          로그인
        </Link>
      )}
    </>
  );
}
