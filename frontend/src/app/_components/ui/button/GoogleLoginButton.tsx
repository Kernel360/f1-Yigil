'use client';

import GoogleLogo from '@/../public/loginBtns/google_login.svg';

export default function GoogleLoginButton() {
  return (
    <button className="w-full py-3 rounded-2xl bg-white border-none flex gap-2 justify-center items-center hover:cursor-pointer hover:bg-[#e6e6e6] active:bg-[#cccccc]">
      <GoogleLogo className="w-6 h-6" />
      <span className="text-lg leading-none">Google 로그인</span>
    </button>
  );
}
