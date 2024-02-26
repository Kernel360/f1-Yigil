'use client';
import { useRouter } from 'next/navigation';
import React, { useEffect, useLayoutEffect, useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import HeaderLogo from '/public/logo/yigil_logo.svg';
import Link from 'next/link';
import { EventFor } from '@/types/type';
import PopOver from '../ui/popover/PopOver';
import { headerPopOverData } from '../ui/popover/constants';
import AddIcon from '/public/icons/add.svg'; // 지울것
import { TUserInfo } from '../mypage/types';
import { authenticateUser } from '../mypage/hooks/myPageActions';
import { useSession } from 'next-auth/react';

export default function HeaderClient() {
  const router = useRouter();

  const [isModalOpened, setIsModalOpened] = useState(false);
  const [user, setUser] = useState<TUserInfo | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const onKeyDown = (e: EventFor<'span', 'onKeyDown'>) => {
    if (e.key === 'Enter') setIsModalOpened(true);
    else if (e.key === 'Escape') setIsModalOpened(false);
  };

  const closeModal = () => {
    setIsModalOpened(false);
  };
  useLayoutEffect(() => {
    (async () => {
      const memberInfo = await authenticateUser();
      if (memberInfo.code === 9201) {
        setIsLoading(false);
        return;
      }
      setUser(memberInfo);
      setIsLoading(false);
    })();
  }, []);

  const { data } = useSession();

  if (isLoading)
    return (
      <>
        <div className="ml-4 cursor-pointer" onClick={() => router.push('/')}>
          <HeaderLogo className="w-[145px] h-[48px]" />
        </div>
        <Link href="https://docs.google.com/forms/d/e/1FAIpQLSfsbhvjAjiY_KSTTUrWNcGB8A7gXshwRW0Or7e_vvAbpGBVgg/viewform">
          <AddIcon className="w-6 stroke-white fill-white ml-40" />
        </Link>
        <span className="mr-4">
          <RoundProfile
            img={data?.user?.image || ''}
            size={40}
            height="h-[40px]"
          />
        </span>
      </>
    );

  return (
    <>
      <div className="ml-4 cursor-pointer" onClick={() => router.push('/')}>
        <HeaderLogo className="w-[145px] h-[48px]" />
      </div>
      {/** 지울것 */}
      <Link href="https://docs.google.com/forms/d/e/1FAIpQLSfsbhvjAjiY_KSTTUrWNcGB8A7gXshwRW0Or7e_vvAbpGBVgg/viewform">
        <AddIcon className="w-6 stroke-white fill-white ml-40" />
      </Link>

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
