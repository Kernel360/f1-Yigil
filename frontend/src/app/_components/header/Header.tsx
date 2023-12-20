'use client';
import React, { useEffect, useState } from 'react';
import HeaderIcons from './ui/HeaderIcons';
import HeaderLogo from './ui/HeaderLogo';

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

  console.log(isHeaderFolded, scrollY);
  useEffect(() => {
    window.addEventListener('scroll', scrollYHandler);
    return () => removeEventListener('scroll', scrollYHandler);
  }, []);
  return (
    <div
      className={`w-full ${
        isHeaderFolded ? 'h-[50px] shadow-md' : 'h-[80px]'
      } flex justify-center items-center fixed  duration-300`}
    >
      <HeaderLogo />
      <HeaderIcons />
    </div>
  );
}
