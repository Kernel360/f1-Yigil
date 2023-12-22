'use client';

import Link from 'next/link';
import Image from 'next/image';

import type { ReactNode } from 'react';

export default function NavigationIcon({
  href,
  imgSrc,
  imgAlt,
  children,
}: {
  href: string;
  imgSrc: string;
  imgAlt: string;
  children: ReactNode;
}) {
  const styles = [
    'w-16',
    'flex flex-col gap-2 justify-center items-center',
    'text-xs text-[#000000]/[.4] no-underline',
    'hover:text-main focus:text-main',
  ].join(' ');

  return (
    <Link href={href} className={styles}>
      <Image src={imgSrc} alt={imgAlt} width={32} height={32} />
      {children}
    </Link>
  );
}
