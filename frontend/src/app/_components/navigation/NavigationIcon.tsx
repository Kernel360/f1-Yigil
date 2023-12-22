'use client';

import Link from 'next/link';
import Image from 'next/image';

import { usePathname } from 'next/navigation';

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
  const flexCenter = 'flex flex-col gap-2 justify-center items-center';
  const textStyles = 'text-xs no-underline';

  return (
    <Link href={href} className={`w-16 ${flexCenter} ${textStyles}`}>
      <Image src={imgSrc} alt={imgAlt} width={32} height={32} />
      {children}
    </Link>
  );
}
