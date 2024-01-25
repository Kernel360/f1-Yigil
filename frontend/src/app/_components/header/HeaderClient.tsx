'use client';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import HeaderLogo from '/public/logo/yigil_logo.svg';
import Link from 'next/link';
import { Session } from 'next-auth';
import PopOver from '../ui/popover/PopOver';
import { headerPopOverData } from './constants';

export default function HeaderClient({ session }: { session: Session | null }) {
  const router = useRouter();

  const [isModalOpened, setIsModalOpened] = useState(false);

  return (
    <>
      <div className="ml-4 cursor-pointer" onClick={() => router.push('/')}>
        <HeaderLogo />
      </div>

      {session ? (
        <>
          <span onClick={() => setIsModalOpened(true)}>
            <RoundProfile
              img={session.user?.image as string}
              size={40}
              style="cursor-pointer"
              height="h-[40px]"
            />
          </span>
          {isModalOpened && (
            <PopOver
              popOverData={headerPopOverData}
              setIsModalOpened={setIsModalOpened}
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
