import React from 'react';

import KakaoBtn from '@/app/_components/ui/button/Kakao';
import LoginLogo from '/public/logo/yigil_logo.svg';
import CloseButton from '@/app/_components/ui/button/CloseButton';
import { kakaoOAuthEndpoint } from '@/app/endpoints/api/auth/callback/kakao/constants';
import { naverOAuthEndPoint } from '@/app/endpoints/api/auth/callback/naver/constants';
import GoogleLoginButton from '@/app/_components/ui/button/GoogleLoginButton';
import NaverLoginButton from '@/app/_components/ui/button/NaverLoginButton';
import { googleOAuthEndPoint } from '@/app/endpoints/api/auth/callback/google/constants';

export default async function LoginPage({
  searchParams,
}: {
  searchParams: { status: string };
}) {
  const { KAKAO_ID, GOOGLE_CLIENT_ID, NAVER_SEARCH_ID } = process.env;

  const kakaoHref = await kakaoOAuthEndpoint(KAKAO_ID);
  const googleHref = await googleOAuthEndPoint(GOOGLE_CLIENT_ID);
  const naverHref = await naverOAuthEndPoint(NAVER_SEARCH_ID);

  return (
    <div className="w-full h-full bg-main flex flex-col items-center">
      <CloseButton
        containerStyle="w-full flex justify-end h-1/6 mr-6 mt-5"
        style="cursor-pointer w-12 h-12 stroke-2 stroke-black hover:stroke-gray-700 active:stroke-gray-500"
      />
      <div className="h-2/6 flex flex-col justify-stretch">
        <LoginLogo className="w-[290px] h-[96px]" />
        <div className="grow"></div>
        {searchParams?.status === 'failed' && (
          <div className="text-center pb-5 text-white font-semibold animate-appear">
            로그인에 실패했습니다. <br />
            다시 시도해주세요.
          </div>
        )}
      </div>
      <div className="w-full mx-7 h-2/6">
        <div className="w-full flex gap-x-4 px-8  relative ">
          <div className="border-t-[1px] border-white w-full h-1"></div>
          <div className="absolute top-[-12px] left-1/2 -translate-x-1/2 px-2 text-xl text-white bg-main">
            SNS로 간편로그인
          </div>
        </div>
        <div className="w-full mt-10 px-7 flex flex-col items-center justify-center gap-4">
          <KakaoBtn href={kakaoHref} />
          <NaverLoginButton href={naverHref} />
          <GoogleLoginButton href={googleHref} />
        </div>
      </div>
    </div>
  );
}
