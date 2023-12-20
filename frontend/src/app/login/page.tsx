'use client';

import React from 'react';
import KakaoBtn from '@/app/_components/ui/button/Kakao';

import { useSession } from 'next-auth/react';
import LoginLogo from './_components/ui/LoginLogo';

export default function LoginPage() {
  return (
    <div className="w-full h-screen bg-main flex flex-col justify-center items-center">
      <LoginLogo />
      <div className="text-xl text-[white]">
        서비스를 사용하시려면 로그인 해주세요
      </div>
      <div className="mt-8 flex flex-col items-center justify-center gap-4">
        <KakaoBtn />
      </div>
    </div>
  );
}
