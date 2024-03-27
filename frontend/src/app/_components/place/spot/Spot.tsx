'use client';

import IconWithCounts from '../../IconWithCounts';
import RoundProfile from '../../ui/profile/RoundProfile';
import ImageCarousel from '../../ui/carousel/ImageCarousel';

import Reaction from '../../reaction/Reaction';

import StarIcon from '/public/icons/star.svg';

import type { TSpot } from '@/types/response';

export default function Spot({
  placeId,
  data,
}: {
  placeId: number;
  data: TSpot;
}) {
  const {
    id,
    image_urls,
    description,
    owner_profile_image_url,
    owner_nickname,
    rate,
    liked,
    create_date,
  } = data;

  return (
    <article className="py-2 flex flex-col gap-4">
      <div className="px-4 flex justify-between items-center">
        <div className="flex gap-2 items-center">
          <RoundProfile
            img={owner_profile_image_url}
            size={40}
            height="h-[40px]"
          />
          <span>{owner_nickname}</span>
        </div>
        <IconWithCounts
          icon={
            <StarIcon className="w-6 h-6 stroke-[#FACC15] fill-[#FACC15] [stroke-linecap:round] [stroke-linejoin:round] " />
          }
          count={rate}
          rating
        />
      </div>
      <div className="px-6 flex flex-col gap-2">
        <ImageCarousel
          images={image_urls}
          label="스팟 이미지들"
          variant="thumbnail"
        />
        <span className="self-end text-gray-400">
          {create_date.toLocaleDateString('ko-KR')}
        </span>
        <div className="p-4 min-h-32 rounded-lg bg-gray-100">{description}</div>
      </div>
      <Reaction placeId={placeId} travelId={id} initialLiked={liked} />
    </article>
  );
}
