'use client';

import HeartIcon from '/public/icons/heart.svg';

export default function LikeButton({
  liked,
  className,
}: {
  liked?: boolean;
  className?: string;
}) {
  return (
    <button className={`${className} p-1`} onClick={() => console.log('click')}>
      <HeartIcon
        className={`w-12 h-12 stroke-white stroke-[3px] ${
          liked && 'fill-white'
        }`}
      />
    </button>
  );
}
