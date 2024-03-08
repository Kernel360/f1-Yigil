'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React from 'react';
import { myPageTabs } from '../constants';
import { checkPath } from './MyPageRoutes';

export default function MyPageTabs() {
  const path = usePathname();

  return (
    <div className=" grid grid-cols-2 items-center">
      {myPageTabs.map(({ label, href }, idx) => (
        <Link
          href={href}
          key={idx}
          tabIndex={0}
          className={`w-full h-[64px] flex justify-center items-center text-2xl cursor-pointer ${
            checkPath(path, href, 1)
              ? 'text-black border-b-2 border-black'
              : 'text-gray-300 border-b-2 border-white'
          }`}
        >
          {label}
        </Link>
      ))}
    </div>
  );
}
