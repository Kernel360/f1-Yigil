import LikeButton from './LikeButton';

import CommentIcon from '/public/icons/comment.svg';
import HeartIcon from '/public/icons/heart.svg';
import StarIcon from '/public/icons/star.svg';

import type { ComponentType, ReactElement } from 'react';

type TRegion =
  | '강원'
  | '경기'
  | '경남'
  | '경북'
  | '전남'
  | '전북'
  | '제주'
  | '충남'
  | '충북';

export interface TPost {
  id: string;
  region: TRegion;
  liked: boolean;
  imageUrl: string;
  title: string;
  likeCount: number;
  commentCount: number;
  rating: number;
}

function IconWithCounts({
  icon,
  count,
  rating,
}: {
  icon: ReactElement;
  count: number;
  rating?: boolean;
}) {
  const label = rating ? count.toFixed(1) : count >= 100 ? '99+' : count;

  return (
    <div className="flex items-center">
      {icon}
      <p className="pl-1 flex justify-center">{label}</p>
    </div>
  );
}

// 외부 placeholder 이미지 사용중, no-img-element 린트 에러 발생
// 차후 next/image 사용하게 변경 예정
export default function Post({
  region,
  liked,
  imageUrl,
  title,
  likeCount,
  commentCount,
  rating,
}: TPost) {
  return (
    <article className="w-[300px] h-[350px] p-2 relative flex shrink-0 flex-col gap-2">
      <span className="absolute top-5 left-4 bg-white px-4 py-1 rounded-full">
        {region}
      </span>
      <LikeButton liked={liked} />
      <img
        className="rounded-lg shadow-lg max-w-full"
        src={imageUrl}
        alt={`${title} 대표 이미지`}
      />
      <section className="flex justify-between items-center gap-2 px-4">
        <p className="text-xl truncate">{title}</p>
        <div className="flex gap-2">
          <IconWithCounts
            icon={<CommentIcon className="w-4 h-4" />}
            count={commentCount}
          />
          <IconWithCounts
            icon={<HeartIcon className="w-4 h-4" />}
            count={likeCount}
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
