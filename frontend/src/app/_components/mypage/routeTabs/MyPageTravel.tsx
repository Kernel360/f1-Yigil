'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React from 'react';
import { myPageTravelTabs } from '../constants';
import { checkPath } from './MyPageRoutes';

export default function MyPageTravel() {
  const path = usePathname();

  return (
    <div className="inline self-center bg-gray-200 rounded-md py-2 px-1 my-2">
      {myPageTravelTabs.map(({ href, label }) => (
        <Link
          key={href}
          href={href}
          className={`bg-gray-200 px-8 py-2 my-2 ${
            checkPath(path, href, 3)
              ? 'bg-gray-500 text-white font-semibold rounded-md '
              : 'text-gray-500 font-normal'
          }`}
        >
          {label}
        </Link>
      ))}
    </div>
  );
}
