'use client';

import { useState } from 'react';

import HeartIcon from '/public/icons/heart.svg';

export default function LikeButton({ liked }: { liked: boolean }) {
  const [postLiked, setPostLiked] = useState(liked);

  function handleClick() {
    console.log('onClick');
    setPostLiked(!postLiked);
  }

  return (
    <button
      className="p-1 absolute top-4 right-4 border-0 bg-transparent"
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
