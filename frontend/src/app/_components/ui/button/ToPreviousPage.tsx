'use client';
import { useRouter } from 'next/navigation';
import React from 'react';

export default function ToPreviousPage({ style }: { style: string }) {
  const { back } = useRouter();
  return (
    <button className={`${style}`} onClick={() => back()}>
      이전으로 돌아가기
    </button>
  );
}
