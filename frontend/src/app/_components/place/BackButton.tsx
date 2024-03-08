'use client';

import { useRouter } from 'next/navigation';

import ChevronLeftIcon from '/public/icons/chevron-left.svg';

export default function BackButton({ className }: { className?: string }) {
  const { back } = useRouter();

  return (
    <button className={`${className} p-2`} onClick={back}>
      <ChevronLeftIcon className="w-6 h-6 stroke-gray-500 stroke-2 [stroke-linecap:round] [stroke-linejoin:round]" />
    </button>
  );
}
