'use client';

import { signIn } from 'next-auth/react';
import React from 'react';
import KakaoLogo from '/public/loginBtns/kakao_logo.svg';
import Link from 'next/link';
import { kakaoOAuthEndpoint } from '@/app/endpoints/api/auth/callback/kakao/constants';

export default function KakaoBtn({ clientId }: { clientId: string }) {
  return (
    <Link
      className="w-full flex justify-center gap-x-2 items-center rounded-2xl py-3 cursor-pointer border-none bg-[#fee500] hover:bg-[#e6cf00] active:bg-[#ccb800]"
      href={kakaoOAuthEndpoint(clientId)}
    >
      <KakaoLogo className="w-6 h-6" />
      <span className="text-lg leading-none">Kakao 로그인</span>
    </Link>
  );
}
