'use client';

import { signIn } from 'next-auth/react';
import React from 'react';
import KakaoLogo from '/public/loginBtns/kakao_logo.svg';

export default function KakaoBtn() {
  return (
    <button
      className="w-full flex justify-center gap-x-2 items-center rounded-full py-3 cursor-pointer border-none bg-[#fee500] hover:bg-[#e6cf00] active:bg-[#ccb800]"
      onClick={() =>
        signIn('kakao', {
          callbackUrl: '/',
        })
      }
    >
      <KakaoLogo className="w-6 h-6" />
      <span className="text-lg leading-none">Kakao 로그인</span>
    </button>
  );
}
