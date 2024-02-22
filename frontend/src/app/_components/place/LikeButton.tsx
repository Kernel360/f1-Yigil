'use client';

import { useState } from 'react';

import HeartIcon from '/public/icons/heart.svg';

import type { EventFor } from '@/types/type';

export default function LikeButton({
  className,
  liked,
}: {
  className?: string;
  liked: boolean;
}) {
  const [postLiked, setPostLiked] = useState(liked);

  function handleClick(event: EventFor<'button', 'onClick'>) {
    event.stopPropagation();
    console.log('onClick');
    setPostLiked(!postLiked);
  }

  return (
    <button
      className={`${className} p-1 border-0 bg-transparent`}
      onClick={handleClick}
    >
      <HeartIcon
        className={`w-12 h-12 stroke-white stroke-[3px] ${
          postLiked ? 'fill-white' : 'fill-none'
        }`}
      />
    </button>
  );
}
