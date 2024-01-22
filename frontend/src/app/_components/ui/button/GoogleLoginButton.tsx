'use client';

import { signIn } from 'next-auth/react';

import GoogleLogo from '@/../public/loginBtns/google_login.svg';

export default function GoogleLoginButton() {
  return (
    <button
      className="w-[300px] border-none hover:cursor-pointer flex rounded-full px-[24px] py-[8px] items-center"
      onClick={() => signIn('google', { callbackUrl: '/' })}
    >
      <GoogleLogo className="w-6 h-6" />
      <span className="ml-[32px] text-lg">Google 계정으로 로그인</span>
    </button>
  );
}
