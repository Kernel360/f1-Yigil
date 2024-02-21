import Image from 'next/image';

import LikeButton from '@/app/_components/place/LikeButton';
import IconWithCounts from '@/app/_components/IconWithCounts';

import ReviewIcon from '/public/icons/review.svg';
import HeartIcon from '/public/icons/heart.svg';
import StarIcon from '/public/icons/star.svg';
import LocationIcon from '/public/icons/map-pin.svg';

import type { TPlaceDetail } from '@/types/response';

export default function PlaceDetail({ detail }: { detail: TPlaceDetail }) {
  const {
    name,
    address,
    liked,
    image_url,
    map_image_url,
    review_count,
    liked_count,
    rating,
  } = detail;

  return (
    <section className="flex flex-col">
      <div className="relative aspect-square shrink-0">
        <Image
          className="object-cover"
          src={image_url}
          alt={`${name} 대표 이미지`}
          fill
          unoptimized
        />
        {liked && (
          <LikeButton className="absolute top-4 right-4" liked={liked} />
        )}
      </div>
      <div className="p-2 flex flex-col gap-2">
        <h1 className="text-2xl font-semibold">{name}</h1>
        <div className="flex gap-3 items-center">
          <IconWithCounts
            icon={<ReviewIcon className="w-4 h-4" />}
            count={review_count}
          />
          <IconWithCounts
            icon={<HeartIcon className="w-4 h-4" />}
            count={liked_count}
          />
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
            }
            count={rating}
            rating
          />
        </div>
      </div>
      <hr className="border-8" />
      <div className="p-4 flex flex-col">
        <h2 className="text-xl font-medium">지도</h2>
        <div className="h-80 relative">
          <Image
            className="aspect-video"
            src={map_image_url}
            alt={`${name} 위치 이미지`}
            fill
            unoptimized
          />
        </div>
        <span className="flex items-center gap-2">
          <LocationIcon className="w-6 h-6 stroke-gray-500" />
          <p className="text-gray-500">{address}</p>
        </span>
      </div>
    </section>
  );
}
