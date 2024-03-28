'use client';
import React from 'react';
import { myPageFavoriteTabs } from '../constants';
import Link from 'next/link';
import { checkPath } from './MyPageRoutes';
import { usePathname } from 'next/navigation';

export default function MyPageFavoriteRoutes() {
  const path = usePathname();
  return (
    <div className="w-[50%] flex justify-between">
      {myPageFavoriteTabs.map(({ label, href }) => (
        <Link
          key={href}
          href={href}
          className={`w-full text-center text-xl ${
            checkPath(path, href, 2)
              ? 'text-gray-700 font-semibold'
              : 'text-gray-300 font-normal'
          }`}
        >
          {label}
        </Link>
      ))}
    </div>
  );
}
