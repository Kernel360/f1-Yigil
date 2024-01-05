'use client';
import React, { useEffect, useState } from 'react';
import HeaderIcons from './ui/HeaderIcons';

import HeaderLogo from '/public/logo/header-logo.svg';

export default function Header() {
  const [isHeaderFolded, setIsHeaderFolded] = useState(false);

  const scrollYHandler = () => {
    const scrollY = window.scrollY;
    if (scrollY >= 40) {
      setIsHeaderFolded(true);
      return;
    }
    if (scrollY == 0) setIsHeaderFolded(false);
  };

  useEffect(() => {
    window.addEventListener('scroll', scrollYHandler);
    return () => removeEventListener('scroll', scrollYHandler);
  }, []);
  return (
    <div
      className={`w-full ${
        isHeaderFolded ? 'h-[50px] shadow-md' : 'h-[80px]'
      } flex justify-center items-center fixed top-0 duration-300 py-1 bg-[white]`}
    >
      <HeaderLogo />
      <HeaderIcons />
    </div>
  );
}
