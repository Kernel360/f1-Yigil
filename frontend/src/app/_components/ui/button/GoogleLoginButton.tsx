import Image from 'next/image';

import googleLogo from '@/../public/loginBtns/google_login.svg';

export default function GoogleLoginButton() {
  return (
    <button className="w-[300px] border-none hover:cursor-pointer flex rounded-full px-[24px] py-[8px] items-center">
      <Image src={googleLogo} alt="Google Logo" width={24} height={24} />
      <span className="ml-[32px] text-lg">Google 계정으로 로그인</span>
    </button>
  );
}
