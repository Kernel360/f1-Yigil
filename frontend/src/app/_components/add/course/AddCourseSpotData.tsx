'use client';

import { useState } from 'react';

import ImageCarousel from '../../ui/carousel/ImageCarousel';
import IconWithCounts from '../../IconWithCounts';

import ChevronDownIcon from '/public/icons/chevron-down.svg';
import StarIcon from '/public/icons/star.svg';

import type { TSpotState } from '@/context/travel/schema';

export default function AddCourseSpotData({
  spot,
  index,
}: {
  spot: TSpotState;
  index: number;
}) {
  const [isOpen, setIsOpen] = useState(false);

  const { place, images, review } = spot;

  const imageUris = images.map((image) => image.uri);
  const currentDate = new Date(Date.now());

  return (
    <article className="flex flex-col">
      <button
        className="px-4 py-2 w-full border border-gray-500 rounded-lg flex justify-between items-center"
        onClick={() => setIsOpen(!isOpen)}
      >
        <div className="flex gap-4">
          {/* ICON */}
          <span>{place.name}</span>
        </div>
        <span>
          <ChevronDownIcon
            className={`w-4 h-4 stroke-gray-500 stroke-2 [stroke-linecap:round] [stroke-linejoin:round] ${
              isOpen && 'rotate-180'
            }`}
          />
        </span>
      </button>
      {isOpen && (
        <section className="py-4 flex flex-col gap-4">
          <div className="px-4 flex justify-between items-center">
            <span className="text-gray-500 font-medium">{place.address}</span>
            <span className="pl-4 shrink-0">
              <IconWithCounts
                icon={
                  <StarIcon className="w-4 h-4 stroke-[#FACC15] fill-[#FACC15]" />
                }
                count={review.rate}
                rating
              />
            </span>
          </div>
          <div className="pl-2">
            <ImageCarousel images={imageUris} label="" variant="thumbnail" />
          </div>
          <span className="self-end text-gray-400 font-medium">
            {currentDate.toLocaleDateString()}
          </span>
          <div className="h-36">
            <div className="h-full p-4 rounded-xl bg-gray-100">
              {review.content}
            </div>
          </div>
        </section>
      )}
    </article>
  );
}
