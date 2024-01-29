import React from 'react';

import KakaoBtn from '@/app/_components/ui/button/Kakao';
import GoogleLoginButton from '@/app/_components/ui/button/GoogleLoginButton';
import LoginLogo from '/public/logo/yigil_logo.svg';
import CloseButton from '@/app/_components/ui/button/CloseButton';

export default function LoginPage() {
  return (
    <div className="w-full h-screen bg-main flex flex-col items-center">
      <CloseButton
        containerStyle="mt-5 mr-6 self-end"
        style="w-12 h-12 stroke-2 stroke-black hover:stroke-gray-700 active:stroke-gray-500"
      />
      <div className="mt-[116px] mb-[202px]">
        <LoginLogo className="w-[290px] h-[96px]" />
      </div>
      <div className="w-full flex gap-x-4 px-8 mx-7 relative">
        <div className="border-t-[1px] border-white w-full h-1"></div>
        <div className="absolute top-[-12px] left-1/2 -translate-x-1/2 px-2 text-xl text-white bg-main">
          SNS로 간편로그인
        </div>
      </div>
      <div className="w-full mt-10 px-7 flex flex-col items-center justify-center gap-4">
        <KakaoBtn />
        <GoogleLoginButton />
      </div>
    </div>
  );
}
