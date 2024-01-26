'use client';

import { useState } from 'react';

import type { EventFor } from '@/types/type';

import HeartIcon from '/public/icons/heart.svg';

export default function LikeButton({ liked }: { liked: boolean }) {
  const [postLiked, setPostLiked] = useState(liked);

  function handleClick(event: EventFor<'button', 'onClick'>) {
    console.log('onClick');
    setPostLiked(!postLiked);
  }

  return (
    <button
      className="p-1 absolute top-4 right-6 border-0 bg-transparent"
      onClick={handleClick}
    >
      <HeartIcon
        className={`w-8 h-8 stroke-white ${
          postLiked ? 'fill-white' : 'fill-none'
        }`}
      />
    </button>
  );
}
