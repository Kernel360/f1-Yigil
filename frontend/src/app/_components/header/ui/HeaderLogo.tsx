import Image from 'next/image';
import React from 'react';
import headerLogo from '/public/logo/header-logo.svg';

export default function HeaderLogo() {
  return <Image src={headerLogo} alt="yigil-header-logo" />;
}
