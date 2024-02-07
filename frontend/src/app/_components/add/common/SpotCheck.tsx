'use client';

import { useContext } from 'react';
import Image from 'next/image';
import useEmblaCarousel from 'embla-carousel-react';

import IconWithCounts from '../../IconWithCounts';
import { AddSpotContext } from '../spot/SpotContext';

import StarIcon from '/public/icons/star.svg';

export default function SpotCheck() {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });

  const { name, address, spotMapImageUrl, images, coords, rating, review } =
    useContext(AddSpotContext);

  const currentDate = new Date(Date.now()).toLocaleDateString();

  return (
    <section className="p-8 flex flex-col gap-5 grow justify-between">
      <div className="flex justify-between items-center">
        <span className="text-2xl font-semibold">{name}</span>
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
      <p className="text-gray-500">{address}</p>
      <div className="relative h-1/3">
        <Image
          className="rounded-md"
          unoptimized
          src={spotMapImageUrl}
          alt="Spot map image"
          fill
        />
      </div>
      <div className="overflow-hidden" ref={emblaRef}>
        <div className="flex gap-2">
          {images.map((image) => (
            <div
              key={image.filename}
              className="p-2 relative w-1/3 overflow-hidden aspect-square rounded-2xl border-2 border-gray-300 shrink-0"
            >
              <Image unoptimized src={image.uri} alt="Uploaded image" fill />
            </div>
          ))}
        </div>
      </div>
      <span className="self-end pr-4 text-gray-400">{currentDate}</span>
      <div className="p-4 h-1/5 flex flex-col gap-2 bg-gray-100 rounded-xl text-lg justify-self-end">
        <span>리뷰 내용</span>
        {review.review}
      </div>
    </section>
  );
}
