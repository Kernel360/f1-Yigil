import Link from 'next/link';
import Image from 'next/image';

import LikeButton from './LikeButton';
import IconWithCounts from '../IconWithCounts';

import ReviewIcon from '/public/icons/review.svg';
import HeartIcon from '/public/icons/heart.svg';
import StarIcon from '/public/icons/star.svg';

import type { TPlace } from '@/types/response';

export default function Place({
  data,
  order,
  variant,
}: {
  data: TPlace;
  order: number;
  variant: 'primary' | 'secondary';
}) {
  const { id, liked, image_url, name, liked_count, review_count, rating } =
    data;

  const postSize = variant === 'primary' ? 'w-[350px]' : 'w-[300px]';

  return (
    <article
      className={`${postSize} px-2 py-4 relative flex shrink-0 flex-col gap-2`}
    >
      <div className="relative aspect-square">
        <Link href={`/places/${id}`}>
          <Image
            className="rounded-lg w-full select-none"
            src={image_url}
            alt={`${name} 대표 이미지`}
            unoptimized
            fill
          />
        </Link>
        {liked && (
          <LikeButton className="absolute top-4 right-4" liked={liked} />
        )}
        {variant === 'primary' && (
          <Link href={`places/${id}`}>
            <span className="absolute left-6 bottom-2 select-none text-white text-8xl font-semibold">
              {order + 1}
            </span>
          </Link>
        )}
      </div>
      <section className="flex flex-col gap-2 px-4">
        <Link
          className="w-fit text-gray-500 text-xl font-medium truncate select-none hover:underline"
          href={`places/${id}`}
        >
          {name}
        </Link>
        <div className="flex gap-3">
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
      </section>
    </article>
  );
}
