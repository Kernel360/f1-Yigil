'use client';
import { useRouter } from 'next/navigation';
import React, { useEffect, useLayoutEffect, useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import HeaderLogo from '/public/logo/yigil_logo.svg';
import Link from 'next/link';
import { EventFor } from '@/types/type';
import PopOver from '../ui/popover/PopOver';
import { headerPopOverData } from '../ui/popover/constants';
import { TUserInfo } from '../mypage/types';
import { authenticateUser } from '../mypage/hooks/myPageActions';

export default function HeaderClient() {
  const router = useRouter();

  const [isModalOpened, setIsModalOpened] = useState(false);
  const [user, setUser] = useState<TUserInfo | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isAnimating, setIsAnimating] = useState(false);

  const onKeyDown = (e: EventFor<'span', 'onKeyDown'>) => {
    if (e.key === 'Enter') setIsModalOpened(true);
    else if (e.key === 'Escape') setIsModalOpened(false);
  };
  //
  const closeModal = () => {
    setIsModalOpened(false);
  };

  useEffect(() => {
    if (!isLoading) {
      setIsAnimating(true);
      const timer = setTimeout(() => {
        setIsAnimating(false);
      }, 500);
      return () => clearTimeout(timer);
    }
  }, [isLoading]);

  useLayoutEffect(() => {
    (async () => {
      const memberInfo = await authenticateUser();
      if (memberInfo?.code === 9201) {
        setIsLoading(false);

        return;
      }
      setUser(memberInfo);
      setIsLoading(false);
    })();
  }, []);

  return (
    <>
      <div className="ml-4 cursor-pointer" onClick={() => router.push('/')}>
        <HeaderLogo className="w-[145px] h-[48px]" />
      </div>

      {isLoading ? (
        <div className="flex space-x-2 justify-center items-center mr-4">
          <div className="h-2.5 w-2.5 bg-gray-500 rounded-full animate-pulse"></div>
          <div className="h-2.5 w-2.5 bg-gray-500 rounded-full animate-pulse delay-150"></div>
          <div className="h-2.5 w-2.5 bg-gray-500 rounded-full animate-pulse delay-300"></div>
        </div>
      ) : user ? (
        <>
          <span
            className={`mr-4 ${
              isAnimating
                ? 'translate-y-[25px] opacity-0'
                : 'translate-y-0 opacity-100 duration-500 ease-in-out transition-all'
            }`}
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
          className={`flex justify-center items-center w-[68px] h-10 mr-4 border-[1px] border-white border-solid rounded-md text-white text-xl no-underline
          ${
            isAnimating
              ? 'translate-y-[25px] opacity-0'
              : 'translate-y-0 opacity-100 duration-500 ease-in-out transition-all'
          }`}
        >
          로그인
        </Link>
      )}
    </>
  );
}
