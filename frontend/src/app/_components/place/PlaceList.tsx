'use client';

import useEmblaCarousel from 'embla-carousel-react';

import { Place } from '.';

import type { TPlace } from '@/types/response';
import Link from 'next/link';

export default function SpotList({
  title,
  data,
  variant,
  moreUrl,
  isLoggedIn,
}: {
  title: string;
  data: TPlace[];
  variant: 'primary' | 'secondary';
  moreUrl: 'popular' | 'region';
  isLoggedIn: boolean;
}) {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });
  return (
    <section className="flex flex-col" aria-label="posts">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 text-3xl font-medium">{title}</span>
        <Link href={`places/${moreUrl}`}>더보기</Link>
      </div>
      <div className="overflow-hidden px-4" ref={emblaRef}>
        <div className="flex">
          {data.map((post, index) => (
            <Place
              key={post.id}
              data={post}
              order={index}
              variant={variant}
              isLoggedIn={isLoggedIn}
            />
          ))}
        </div>
      </div>
    </section>
  );
}
