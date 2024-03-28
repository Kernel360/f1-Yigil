'use client';

import Link from 'next/link';
import Image from 'next/image';

import IconWithCounts from '../IconWithCounts';
import LikeButton from './LikeButton';

import type { TCourseDetail } from '@/types/response';

import StarIcon from '/public/icons/star.svg';
import ReviewIcon from '/public/icons/review.svg';

export default function Course({ data }: { data: TCourseDetail }) {
  const { id, title, map_static_image_url, spot_count, rate, liked } = data;
  return (
    <article className="p-4 flex flex-col gap-2">
      <Link className="relative aspect-video" href={`/detail/courses/${id}`}>
        <Image
          className="rounded-2xl"
          src={map_static_image_url}
          alt={`${title} 코스 지도 이미지`}
          fill
          sizes="400px"
        />
        <LikeButton
          travelId={id}
          liked={liked}
          position="top-4 right-4"
          sizes="w-12 h-12"
        />
      </Link>
      <Link
        className="px-2 w-full text-gray-500 text-xl font-medium truncate select-none hover:underline text-ellipsis"
        href={`/detail/courses/${id}`}
      >
        {title}
      </Link>
      <div className="px-2 flex gap-4">
        <IconWithCounts
          count={spot_count}
          icon={<ReviewIcon className="w-4 h-4" />}
        />{' '}
        <IconWithCounts
          count={rate}
          icon={
            <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
          }
          rating
        />
      </div>
    </article>
  );
}
