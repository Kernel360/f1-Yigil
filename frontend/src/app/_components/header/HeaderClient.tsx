'use client';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import HeaderLogo from '/public/logo/yigil_logo.svg';
import Link from 'next/link';
import { Session } from 'next-auth';
import { headerPopOverData } from './constants';
import { EventFor } from '@/types/type';
import ViewPortal from '../Portal';

export default function HeaderClient({ session }: { session: Session | null }) {
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

      {session ? (
        <>
          <span
            className="mr-4"
            onClick={() => setIsModalOpened(true)}
            onKeyDown={onKeyDown}
            tabIndex={0}
          >
            <RoundProfile
              img={session.user?.image as string}
              size={40}
              style="cursor-pointer"
              height="h-[40px]"
            />
          </span>
          {isModalOpened && (
            <ViewPortal
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
