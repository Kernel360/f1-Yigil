'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

import React from 'react';
import { myPageRoutes } from '../constants';

function checkPath(path: string, href: string) {
  const pathRoot = path.slice(1).split('/')[0];
  const compareHref = href.slice(1).split('/')[0];

  return pathRoot === compareHref;
}

export default function MyPageRoutes() {
  const path = usePathname();

  return (
    <>
      {myPageRoutes.map(({ href, label }) => (
        <Link
          key={href}
          href={href}
          className={`text-[28px] font-semibold ${
            checkPath(path, href)
              ? 'border-b-4 border-black text-black'
              : 'border-b-4 border-white text-gray-300 '
          }`}
        >
          {label}
        </Link>
      ))}
    </>
  );
}
