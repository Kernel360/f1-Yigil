'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React, { useState } from 'react';
import { myPageTabs } from './constants';
import MyPageMyPlace from './MyPlace';
import MyPageStoredPlace from './StoredPlace';

export default function MyPageContent() {
  const path = usePathname();

  return (
    <>
      <div className=" grid grid-cols-2 items-center">
        {myPageTabs.map(({ label, href }, idx) => (
          <Link
            href={href}
            key={idx}
            tabIndex={0}
            className={`w-full h-[64px] flex justify-center items-center text-2xl cursor-pointer ${
              path.includes(href)
                ? 'text-black border-b-2 border-black'
                : 'text-gray-300 border-b-2 border-white'
            }`}
          >
            {label}
          </Link>
        ))}
      </div>
      {/* {tabState === 0 ? (
        <div className="">
          <MyPageMyPlace />
        </div>
      ) : (
        <div className="mt-3">
          <MyPageStoredPlace />
        </div>
      )} */}
    </>
  );
}
