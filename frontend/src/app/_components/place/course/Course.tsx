'use client';

import Image from 'next/image';

import RoundProfile from '../../ui/profile/RoundProfile';
import IconWithCounts from '../../IconWithCounts';
import Reaction from '../../reaction/Reaction';

import type { TCourse } from '@/types/response';

import StarIcon from '/public/icons/star.svg';

export default function Course({
  placeId,
  data,
}: {
  placeId: number;
  data: TCourse;
}) {
  const {
    id,
    title,
    map_static_image_file_url,
    owner_nickname,
    owner_profile_image_url,
    rate,
    create_date,
    content,
    liked,
    owner_id,
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
      <div className="mx-4 relative aspect-video">
        <Image
          className="rounded-xl"
          src={map_static_image_file_url}
          alt={`${title} 경로 이미지`}
          fill
        />
      </div>
      <div className="px-6 flex flex-col gap-2">
        <span className="self-end text-gray-400">{create_date}</span>
        <div className="p-4 min-h-32 rounded-lg bg-gray-100">{content}</div>
      </div>
      <Reaction placeId={placeId} travelId={id} initialLiked={liked} />
    </article>
  );
}
