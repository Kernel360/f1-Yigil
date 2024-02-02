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
            !path.includes(href) && 'text-gray-300 bg-gray-200'
          } `}
        >
          {label}
        </Link>
      ))}
    </div>
  );
}
