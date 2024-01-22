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
      className={`rounded-full mr-4 ${style} ${height}`}
      src={img || '/icons/profile.svg'}
      alt="profile"
      width={size}
      height={size}
    />
  );
}
