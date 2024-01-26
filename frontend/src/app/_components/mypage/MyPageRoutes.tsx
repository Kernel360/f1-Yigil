'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

import React from 'react';
import { myPageRoutes } from './constants';

export default function MyPageRoutes() {
  const path = usePathname();

  return (
    <>
      {myPageRoutes.map(({ href, label }) => (
        <Link
          key={href}
          href={href}
          className={`text-[28px] font-semibold ${
            path === href
              ? 'border-b-4 border-black text-black'
              : 'border-b-4 border-white text-gray-300'
          }`}
        >
          {label}
        </Link>
      ))}
    </>
  );
}
