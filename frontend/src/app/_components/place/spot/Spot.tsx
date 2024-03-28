'use client';

import { useContext } from 'react';
import { MemberContext } from '@/context/MemberContext';
import { ReportContext } from '@/context/ReportContext';

import IconWithCounts from '../../IconWithCounts';
import RoundProfile from '../../ui/profile/RoundProfile';
import ImageCarousel from '../../ui/carousel/ImageCarousel';

import FollowButton from '../FollowButton';
import ReportButton from '../ReportButton';
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
  const memberStatus = useContext(MemberContext);
  const reportTypes = useContext(ReportContext);
  const {
    id: spotId,
    image_urls,
    description,
    owner_id,
    owner_profile_image_url,
    owner_nickname,
    rate,
    liked,
    create_date,
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
        {reportTypes && reportTypes.length !== 0 && (
          <div className="px-2 flex justify-end">
            <ReportButton parentId={spotId} />
          </div>
        )}
      </div>
      <Reaction placeId={placeId} travelId={spotId} initialLiked={liked} />
    </article>
  );
}
