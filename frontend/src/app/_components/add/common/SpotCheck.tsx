'use client';

import useEmblaCarousel from 'embla-carousel-react';

import IconWithCounts from '../../IconWithCounts';

import StarIcon from '/public/icons/star.svg';
import Image from 'next/image';

type TRating = 1 | 2 | 3 | 4 | 5;

interface TSpotData {
  name: string;
  roadAddress: string;
  spotMapImageUrl: string;
  spotImages: string[] | File[];
  coords: [number, number];
  rating: TRating;
  review: string;
}

export default function SpotCheck({
  name,
  roadAddress,
  spotMapImageUrl,
  spotImages,
  coords,
  rating,
  review,
}: TSpotData) {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });

  const images = spotImages.map((image) => {
    if (typeof image === 'string') {
      return image;
    }

    return image.name;
  });

  return (
    <section className="p-4 flex flex-col gap-2">
      <div className="flex justify-between">
        <span>{name}</span>
        <span>
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
            }
            count={rating}
            rating
          />
        </span>
      </div>
      <p>{roadAddress}</p>
      {/* Static Image */}
      <div className="overflow-hidden" ref={emblaRef}>
        <div className="flex">
          {images.map((image) => (
            <div className="relative aspect-square rounded-2xl border-2 border-gray-500">
              <Image src={image} alt="Uploaded image" fill />
            </div>
          ))}
        </div>
      </div>
      <div className="p-2 h-1/4 flex flex-col gap-2">
        <span>리뷰 내용</span>
        {review}
      </div>
    </section>
  );
}
