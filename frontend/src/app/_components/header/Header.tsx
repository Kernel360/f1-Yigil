import HeaderIcons from './ui/HeaderIcons';

import HeaderLogo from '/public/logo/header-logo.svg';

export default function Header() {
  return (
    <div className="w-full h-[80px] flex justify-center items-center">
      <HeaderLogo />
      <HeaderIcons />
    </div>
  );
}
