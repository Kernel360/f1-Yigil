import Image from 'next/image';
import React from 'react';

interface PropsType {
  img?: string;
  size?: number;
  style?: string;
  height?: string;
}

export default function RoundProfile({ img, size, style, height }: PropsType) {
  return (
    <Image
      className={`rounded-full ${style} ${height}`}
      src={img || '/public/icons/profile.svg'}
      alt="profile"
      width={size}
      height={size}
    />
  );
}
