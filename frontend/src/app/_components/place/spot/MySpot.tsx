'use client';

import { TMySpotForPlace } from '@/types/response';
import { useState } from 'react';

import ChevronDownIcon from '/public/icons/chevron-down.svg';
import StarIcon from '/public/icons/star.svg';
import ImageCarousel from '../../ui/carousel/ImageCarousel';

export default function MySpot({
  placeName,
  data,
}: {
  placeName: string;
  data: TMySpotForPlace;
}) {
  const [collapsed, setCollapsed] = useState(true);

  const { rate, image_urls, description, create_date } = data;

  const rateNum = Number.parseInt(rate, 10);

  return (
    <section className="px-4 py-2 flex flex-col gap-4" aria-label="나의 기록">
      <div className="flex justify-between">
        <h2 className="text-xl font-medium">나의 기록</h2>
        <button onClick={() => setCollapsed(!collapsed)}>
          <ChevronDownIcon
            className={`w-4 h-4 stroke-black [stroke-linecap:round] [stroke-linejoin:round] ${
              !collapsed && 'rotate-180'
            } transition-transform `}
          />
        </button>
      </div>
      <div className="flex gap-4">
        {Array.from(Array(5).keys())
          .map((num) => num + 1)
          .map((num) => (
            <StarIcon
              className={`w-12 h-12 [stroke-linecap:round] [stroke-linejoin:round] ${
                num <= rateNum
                  ? 'stroke-[#FACC15] fill-[#FACC15]'
                  : 'stroke-gray-200 fill-gray-200'
              }`}
              key={num}
            />
          ))}
      </div>
      {!collapsed && (
        <div className="flex flex-col gap-4">
          <ImageCarousel
            images={image_urls}
            label={placeName}
            variant="thumbnail"
          />
          <span className="self-end">{create_date.toLocaleDateString()}</span>
          <p className="min-h-28 p-4 bg-gray-100 rounded-lg">{description}</p>
        </div>
      )}
    </section>
  );
}
