'use client';

import { signIn } from 'next-auth/react';
import Image from 'next/image';
import React from 'react';
import kakaoLogo from '../../../../../public/loginBtns/kakao_logo.png';

export default function KakaoBtn() {
  return (
    <button
      className="bg-[#fee500] w-[300px] h-[45px] flex justify-start text-center items-center rounded-md p-[15px] cursor-pointer border-none"
      onClick={() =>
        signIn('kakao', {
          callbackUrl: '/',
        })
      }
    >
      <span className="mx-2 flex items-center ">
        <Image src={kakaoLogo} alt="logo" width={25} height={25} />
      </span>
      <span className="ml-[40px] text-lg text-center">Kakao 로그인하기</span>
    </button>
  );
}
