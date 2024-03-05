import Image from 'next/image';

import BookmarkButton from '@/app/_components/place/BookmarkButton';
import IconWithCounts from '@/app/_components/IconWithCounts';

import ReviewIcon from '/public/icons/review.svg';
import StarIcon from '/public/icons/star.svg';
import LocationIcon from '/public/icons/map-pin.svg';

import type { TPlaceDetail } from '@/types/response';

export default function PlaceDetail({
  detail,
  isLoggedIn,
}: {
  detail: TPlaceDetail;
  isLoggedIn: boolean;
}) {
  const {
    place_name,
    address,
    bookmarked,
    thumbnail_image_url,
    map_static_image_url,
    review_count,
    rate,
  } = detail;

  return (
    <section className="flex flex-col">
      <div className="relative aspect-square shrink-0">
        <Image
          className="object-cover"
          src={thumbnail_image_url}
          alt={`${place_name} 대표 이미지`}
          fill
        />
        <BookmarkButton
          className="absolute top-4 right-4"
          bookmarked={bookmarked}
          isLoggedIn={isLoggedIn}
        />
      </div>
      <div className="px-4 py-2 flex flex-col gap-2">
        <h1 className="text-2xl font-semibold select-all">{place_name}</h1>
        <div className="flex gap-3 items-center">
          <IconWithCounts
            icon={<ReviewIcon className="w-4 h-4" />}
            count={review_count}
          />
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
            }
            count={rate}
            rating
          />
        </div>
      </div>
      <hr className="border-8" />
      <div className="h-80 p-4 flex flex-col gap-4">
        <h2 className="text-xl font-medium">지도</h2>
        <div className="relative grow">
          <Image
            className="object-cover"
            src={map_static_image_url}
            alt={`${place_name} 위치 이미지`}
            fill
          />
        </div>
        <span className="flex items-center gap-2">
          <LocationIcon className="w-6 h-6 stroke-gray-500" />
          <p className="text-gray-500 select-all">{address}</p>
        </span>
      </div>
    </section>
  );
}
