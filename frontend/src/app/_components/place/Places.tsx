'use client';

import useEmblaCarousel from 'embla-carousel-react';

import Place from './Place';

import type { TPlace } from '@/types/response';

export default function Places({
  data,
  variant,
  isLoggedIn,
}: {
  data: TPlace[];
  variant?: 'primary' | 'secondary';
  isLoggedIn: boolean;
}) {
  const [emblaRef] = useEmblaCarousel();

  return (
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
  );
}
