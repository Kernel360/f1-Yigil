'use client';

import useEmblaCarousel from 'embla-carousel-react';
import RegionIcon from './RegionIcon';

import { regions } from './constants';

export default function RegionLinks() {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });

  return (
    <nav className="pt-4 flex flex-col gap-y-4" aria-label="regions">
      <span className="self-center">어디로 떠나볼까요?</span>
      <div className="overflow-hidden" ref={emblaRef}>
        <div className="flex justify-between">
          {regions.map(({ slug, label }) => (
            <RegionIcon key={slug} slug={slug} label={label} />
          ))}
        </div>
      </div>
    </nav>
  );
}
