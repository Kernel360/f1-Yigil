'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React, { useEffect, useState } from 'react';
import { myPageMyPlaceTab } from './constants';
import MyPagePlaceList from './MyPagePlaceList';

export default function MyPageMyPlace() {
  const [placeList, setPlaceList] = useState([]);
  const path = usePathname();

  const getData = async () => {
    const res = await fetch('http://localhost:8080/api/my/posts', {
      method: 'GET',
      headers: {
        Authorization: 'Bearer token',
      },
    });
    const result = await res.json();
    setPlaceList(result.data);
  };
  useEffect(() => {
    /**tab state에 따라 장소를 가져올 함수 호출할 지 일정을 가져올 함수 호출할 지 */
    getData();
  }, []);

  return (
    <>
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
    </>
  );
}
