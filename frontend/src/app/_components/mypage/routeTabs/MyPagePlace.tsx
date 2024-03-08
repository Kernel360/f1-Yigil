'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React from 'react';
import { myPageMyTab } from '../constants';
import { checkPath } from './MyPageRoutes';

export default function MyPagePlace() {
  const path = usePathname();

  return (
    <div className="items-center grid grid-cols-4">
      {myPageMyTab.map(({ href, label }, idx) => (
        <Link
          href={href}
          key={idx}
          tabIndex={0}
          className={`py-[14px] flex justify-center text-xl leading-6 cursor-pointer ${
            checkPath(path, href, 2)
              ? 'text-gray-700 font-semibold'
              : 'text-gray-300 font-normal'
          } `}
        >
          {label}
        </Link>
      ))}
    </div>
  );
}
