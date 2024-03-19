'use client';

import Link from 'next/link';
import Places from './Places';

import type { TPlace } from '@/types/response';

import ChevronRightIcon from '/public/icons/chevron-right.svg';

export default function PopularPlaces({
  data,
  isLoggedIn,
}: {
  data: TPlace[];
  isLoggedIn: boolean;
}) {
  return (
    <section className="flex flex-col" aria-label="popular-places">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 pb-2 text-3xl font-medium">인기</span>
        <Link href="places/popular">
          <ChevronRightIcon className="w-6 h-6 stroke-black stroke-2 [stroke-linecap:round] [stroke-linejoin:round]" />
        </Link>
      </div>
      <Places data={data} isLoggedIn={isLoggedIn} variant="primary" carousel />
    </section>
  );
}
