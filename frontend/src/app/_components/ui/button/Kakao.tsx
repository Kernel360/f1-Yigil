'use client';

import { signIn } from 'next-auth/react';
import Image from 'next/image';
import React from 'react';
import kakaoLogo from '/public/loginBtns/kakao_logo.png';

export default function KakaoBtn() {
  return (
    <button
      className="w-[300px] flex justify-start text-center items-center rounded-full px-[24px] py-[8px] cursor-pointer border-none bg-[#fee500] hover:bg-[#e6cf00] active:bg-[#ccb800]"
      onClick={() =>
        signIn('kakao', {
          // callbackUrl: '/',
        })
      }
    >
      <Image src={kakaoLogo} alt="logo" width={24} height={24} />
      <span className="ml-[60px] text-lg text-center">Kakao 로그인</span>
    </button>
  );
}
