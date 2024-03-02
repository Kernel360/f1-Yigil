'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

import BookmarkIcon from '/public/icons/bookmark.svg';

import type { EventFor } from '@/types/type';

export default function BookmarkButton({
  className,
  bookmarked,
  isLoggedIn,
}: {
  className?: string;
  bookmarked: boolean;
  isLoggedIn: boolean;
}) {
  const { push } = useRouter();

  const [placeBookmarked, setPlaceBookmarked] = useState(bookmarked);

  function handleClick(event: EventFor<'button', 'onClick'>) {
    event.stopPropagation();

    if (!isLoggedIn) {
      push('/login');
      return;
    }

    console.log('onClick');
    setPlaceBookmarked(!placeBookmarked);
  }

  return (
    <button
      className={`${className} p-1 border-0 bg-transparent`}
      onClick={handleClick}
    >
      <BookmarkIcon
        className={`w-12 h-12 stroke-white stroke-[3px] ${
          placeBookmarked ? 'fill-white' : 'fill-none'
        }`}
      />
    </button>
  );
}
