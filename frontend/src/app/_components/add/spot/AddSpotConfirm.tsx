'use client';

import Image from 'next/image';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';

import IconWithCounts from '../../IconWithCounts';
import ImageCarousel from '../../ui/carousel/ImageCarousel';

import StarIcon from '/public/icons/star.svg';
import PlaceholderImage from '/public/images/placeholder.png';

export default function AddSpotConfirm() {
  const [spot] = useContext(SpotContext);

  const { place, images, review } = spot;

  const uris =
    images.type === 'new'
      ? images.data.map((image) => image.uri)
      : images.data.map((image) => image);

  const currentDate = new Date(Date.now());

  return (
    <section className="flex flex-col justify-center grow">
      <div className="px-4 py-1 flex justify-between">
        <span className="pl-2 text-2xl font-semibold">{place.name}</span>
        <span>
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 stroke-[#FACC15] fill-[#FACC15]" />
            }
            count={review.rate}
            rating
          />
        </span>
      </div>
      <span className="px-6 py-4 text-gray-500">{place.address}</span>
      <div className="mx-4 relative aspect-video">
        <Image
          className="rounded-xl object-cover"
          priority
          src={place.mapImageUrl}
          placeholder="blur"
          blurDataURL={PlaceholderImage.blurDataURL}
          alt={`${place.name} 지도 이미지`}
          fill
        />
      </div>
      <div className="pl-4 py-4">
        <ImageCarousel images={uris} label="" variant="thumbnail" />
      </div>
      <span className="px-3 pb-2 self-end text-gray-400 font-medium">
        {currentDate.toLocaleDateString()}
      </span>
      <div className="p-4 h-36">
        <div className="h-full p-4 rounded-xl bg-gray-100">
          {review.content}
        </div>
      </div>
    </section>
  );
}
