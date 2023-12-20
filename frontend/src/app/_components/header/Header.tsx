import React from 'react';
import HeaderIcons from './ui/HeaderIcons';
import HeaderLogo from './ui/HeaderLogo';

export default function Header() {
  return (
    <div className="w-full h-[80px] flex justify-center items-center relative">
      <HeaderLogo />
      <HeaderIcons />
    </div>
  );
}
