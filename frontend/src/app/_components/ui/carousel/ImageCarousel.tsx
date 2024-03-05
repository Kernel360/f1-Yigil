'use client';

import useEmblaCarousel from 'embla-carousel-react';
import Image from 'next/image';

function getImageSize(variant: 'primary' | 'secondary' | 'thumbnail') {
  switch (variant) {
    case 'primary':
      return 'w-[350px]';
    case 'secondary':
      return 'w-[300px]';
    case 'thumbnail':
      return 'w-[150px]';
  }
}

export default function ImageCarousel({
  images,
  label,
  variant,
}: {
  images: string[];
  label: string;
  variant: 'primary' | 'secondary' | 'thumbnail';
}) {
  const [emblaRef] = useEmblaCarousel();

  return (
    <div
      className="overflow-hidden"
      ref={emblaRef}
      aria-label={`${label} 이미지들`}
    >
      <div className="flex gap-2">
        {images.map((url, index) => (
          <div
            key={index}
            className={`relative ${getImageSize(
              variant,
            )} aspect-square shrink-0`}
          >
            <Image
              className="rounded-lg"
              src={url}
              alt={`${label} 이미지`}
              fill
              sizes="20vw"
            />
          </div>
        ))}
      </div>
    </div>
  );
}
