'use client';
import { useRouter } from 'next/navigation';
import React from 'react';
import XLogo from '/public/icons/x.svg';
interface TButtonProps {
  containerStyle?: string;
  style?: string;
}

export default function CloseButton({ containerStyle, style }: TButtonProps) {
  const { push } = useRouter();

  return (
    <button className={containerStyle} onClick={() => push('/')}>
      <XLogo className={style} />
    </button>
  );
}
