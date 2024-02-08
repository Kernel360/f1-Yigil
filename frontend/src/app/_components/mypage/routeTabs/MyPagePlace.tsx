'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React from 'react';
import { myPageMyPlaceTab } from '../constants';

export default function MyPagePlace() {
  const path = usePathname();

  return (
    <div className="items-center grid grid-cols-4">
      {myPageMyPlaceTab.map(({ href, label }, idx) => (
        <Link
          href={href}
          key={idx}
          tabIndex={0}
          className={`py-[14px] flex justify-center text-xl leading-6 cursor-pointer ${
            path === href
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
