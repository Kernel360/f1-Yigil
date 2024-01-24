'use client';
import { signOut } from 'next-auth/react';
import { useRouter } from 'next/navigation';
import React, { useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import HeaderLogo from '/public/logo/yigil_logo.svg';
import MapPin from '/public/icons/map-pin.svg';
import Bell from '/public/icons/bell.svg';
import Link from 'next/link';
import { Session } from 'next-auth';

export default function HeaderClient({ session }: { session: Session | null }) {
  const router = useRouter();

  const [isOpenedModal, setIsOpenedModal] = useState(false);
  return (
    <>
      <div className="ml-4 cursor-pointer" onClick={() => router.push('/')}>
        <HeaderLogo />
      </div>
      {session ? (
        <>
          <span onClick={() => setIsOpenedModal(true)}>
            <RoundProfile
              img={session.user?.image as string}
              size={40}
              style="cursor-pointer"
              height="h-[40px]"
            />
          </span>
          {isOpenedModal && (
            <>
              <div
                className="fixed inset-0"
                onClick={() => setIsOpenedModal(false)}
              ></div>
              <div className="absolute bottom-[-90px] right-4 w-[134px] h-[104px] bg-[#F3F4F6] rounded-md flex flex-col items-center justify-center gap-5">
                <Link
                  aria-role="tab0"
                  tabIndex={1}
                  href="/mypage"
                  className="flex items-center"
                  onClick={() => setIsOpenedModal(false)}
                >
                  <div>마이페이지</div>
                  <MapPin />
                </Link>
                <Link
                  aria-role="tab1"
                  tabIndex={2}
                  href="/"
                  className="flex items-center"
                  onClick={() => signOut()}
                >
                  <div>로그아웃</div>
                  <Bell />
                </Link>
              </div>
            </>
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
