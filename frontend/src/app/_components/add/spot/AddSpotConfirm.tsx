'use client';

import Image from 'next/image';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { blobTodataUrl } from '@/utils';

import IconWithCounts from '../../IconWithCounts';
import ImageCarousel from '../../ui/carousel/ImageCarousel';

import StarIcon from '/public/icons/star.svg';
import PlaceholderImage from '/public/images/placeholder.png';

const tempImage =
  'http://cdn.yigil.co.kr/images/a188a3e6-6a33-4013-b97d-103d570f958a_%EC%86%94%ED%96%A5%EA%B8%B0%20%EC%A7%80%EB%8F%84%20%EC%9D%B4%EB%AF%B8%EC%A7%80.png';

export default function AddSpotConfirm() {
  const [spot] = useContext(SpotContext);

  const { place, images, review } = spot;

  const uris = images.map((image) => image.uri);

  const currentData = new Date(Date.now());

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
          src={tempImage}
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
        {currentData.toLocaleDateString()}
      </span>
      <div className="p-4 h-36">
        <div className="h-full p-4 rounded-xl bg-gray-100">
          {review.content}
        </div>
      </div>
    </section>
  );
}
