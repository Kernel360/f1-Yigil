'use client';

import NaverLogo from '@/../public/loginBtns/naver_logo.svg';
import Link from 'next/link';

export default function NaverLoginButton({ href }: { href: string }) {
  return (
    <Link
      href={href}
      className="w-full py-3 rounded-2xl bg-[#03C75A] border-none flex gap-2 justify-center items-center hover:cursor-pointer hover:bg-[#00bc3a] active:bg-[#00ac31]"
    >
      <NaverLogo className="mr-2" />
      <span className="text-lg leading-none text-white py-[2px]">
        네이버 로그인
      </span>
    </Link>
  );
}
