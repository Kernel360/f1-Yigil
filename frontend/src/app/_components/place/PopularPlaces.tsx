'use client';

import Link from 'next/link';
import Places from './Places';

import type { TPlace } from '@/types/response';

export default function PopularPlaces({
  data,
  isLoggedIn,
}: {
  data: TPlace[];
  isLoggedIn: boolean;
}) {
  return (
    <section className="flex flex-col" aria-label="posts">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 text-3xl font-medium">인기</span>
        <Link href="places/popular">더보기</Link>
      </div>
      <Places data={data} isLoggedIn={isLoggedIn} variant="primary" carousel />
    </section>
  );
}
