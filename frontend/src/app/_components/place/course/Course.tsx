'use client';

import { useContext } from 'react';
import { MemberContext } from '@/context/MemberContext';
import Image from 'next/image';

import RoundProfile from '../../ui/profile/RoundProfile';
import IconWithCounts from '../../IconWithCounts';

import FollowButton from '../FollowButton';
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
  const memberStatus = useContext(MemberContext);
  const {
    id: travelId,
    title,
    map_static_image_url,
    owner_id,
    owner_nickname,
    owner_profile_image_url,
    rate,
    create_date,
    content,
    liked,
    following,
  } = data;

  return (
    <article className="py-2 flex flex-col gap-4">
      <div className="px-4 flex justify-between items-center">
        <div className="flex gap-1 items-center">
          <RoundProfile
            img={owner_profile_image_url}
            size={40}
            height="h-[40px]"
          />
          <span className="pl-2 text-lg font-medium">{owner_nickname}</span>
          {memberStatus.isLoggedIn === 'true' &&
            memberStatus.member.member_id !== owner_id && (
              <FollowButton ownerId={owner_id} initialFollowing={following} />
            )}
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
          src={map_static_image_url}
          alt={`${title} 경로 이미지`}
          fill
        />
      </div>
      <div className="px-6 flex flex-col gap-2">
        <span className="self-end text-gray-400">{create_date}</span>
        <div className="p-4 min-h-32 rounded-lg bg-gray-100">{content}</div>
      </div>
      <Reaction placeId={placeId} travelId={travelId} initialLiked={liked} />
    </article>
  );
}
