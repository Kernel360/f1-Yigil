'use client';

import type { EventFor } from '@/types/type';

import HeartIcon from '/public/icons/heart.svg';

export default function LikeButton({ liked }: { liked: boolean }) {
  function handleClick(event: EventFor<'button', 'onClick'>) {
    console.log('onClick');
  }

  return (
    <button
      className="p-1 absolute top-4 right-6 border-0 bg-transparent"
      onClick={handleClick}
    >
      <HeartIcon
        className={`w-6 h-6 stroke-2 stroke-white ${
          liked ? 'fill-white' : 'fill-none'
        }`}
      />
    </button>
  );
}
