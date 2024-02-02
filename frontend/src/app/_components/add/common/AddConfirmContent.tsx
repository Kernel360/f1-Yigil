'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React from 'react';
import InfoTitle from './InfoTitle';

export default function AddConfirmContent() {
  const url = usePathname();

  const text = url.includes('spot')
    ? '장소를 추가했어요!'
    : '일정을 추가했어요!';

  return (
    <>
      <InfoTitle
        label={text.slice(0, 2)}
        additionalLabel={text.slice(2)}
        textSizeAndLineHeight="text-[48px] leading-[60px]"
        fontBold="font-bold"
      />
      <div className="text-2xl ml-10 p-2 leading-9 h-1/3">
        <div>
          <span className="font-bold">마이페이지</span>에서
        </div>
        <div>{text.slice(0, 3)} 확인하세요.</div>
      </div>
      <ul className="mx-10 flex flex-col items-center gap-y-4">
        <Link
          href="/mypage"
          className="w-full py-4 flex justify-center items-center bg-gray-100 text-gray-500 text-2xl leading-7 font-semibold rounded-xl hover:bg-gray-200 hover:text-gray-400 active:bg-gray-300 active:text-white"
        >
          마이페이지로 바로가기
        </Link>
        <Link
          href="/"
          className="w-full py-4 flex justify-center items-center bg-gray-100 text-gray-500 text-2xl leading-7 font-semibold rounded-xl hover:bg-gray-200 hover:text-gray-400 active:bg-gray-300 active:text-white"
        >
          홈으로 바로가기
        </Link>
      </ul>
    </>
  );
}
