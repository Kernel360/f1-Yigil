'use client';

import Link from 'next/link';
import useEmblaCarousel from 'embla-carousel-react';

import { Spot } from '@/app/_components/spot';

import type { TSpot } from './Spot';

export default function SpotList({
  title,
  data,
  variant,
}: {
  title: string;
  data: TSpot[];
  variant: 'primary' | 'secondary';
}) {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });
  return (
    <section className="flex flex-col" aria-label="posts">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 text-3xl font-medium">{title}</span>
        <span>더보기</span>
      </div>
      <div className="overflow-hidden px-4" ref={emblaRef}>
        <div className="flex">
          {data.map((post, index) => (
            <Link href={`#`} key={post.id}>
              <Spot data={post} order={index} variant={variant} />
            </Link>
          ))}
        </div>
      </div>
    </section>
  );
}
