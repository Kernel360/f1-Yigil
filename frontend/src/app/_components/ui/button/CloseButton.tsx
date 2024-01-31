'use client';
import { useRouter } from 'next/navigation';
import React from 'react';
import XLogo from '/public/icons/x.svg';
interface TButtonProps {
  containerStyle?: string;
  style?: string;
}

export default function CloseButton({ containerStyle, style }: TButtonProps) {
  const router = useRouter();

  return (
    <button className={containerStyle} onClick={() => router.back()}>
      <XLogo className={style} />
    </button>
  );
}
