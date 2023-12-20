import Image from 'next/image';

import googleLogo from '@/../public/loginBtns/google_login.svg';

export default function GoogleLoginButton() {
  return (
    <button className="w-[300px] border-none flex rounded-full px-[16px] py-[8px] items-center">
      <Image
        className="self-start"
        src={googleLogo}
        alt="Google Logo"
        width={32}
        height={32}
      />
      <span className="ml-[32px] text-lg">Google 계정으로 로그인</span>
    </button>
  );
}
