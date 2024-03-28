'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

import Spinner from '@/app/_components/ui/Spinner';

import BookmarkIcon from '/public/icons/bookmark.svg';

import type { EventFor } from '@/types/type';
import { postBookmark } from './places/action';
import { postSpotResponseSchema } from '@/types/response';

export default function BookmarkButton({
  className,
  placeId,
  bookmarked,
  isLoggedIn,
  iconSize,
  iconColor,
}: {
  className?: string;
  placeId: number;
  bookmarked: boolean;
  isLoggedIn?: boolean;
  iconSize?: string;
  iconColor?: string;
}) {
  const { push } = useRouter();

  const [placeBookmarked, setPlaceBookmarked] = useState(bookmarked);
  const [isLoading, setIsLoading] = useState(false);

  async function handleClick(event: EventFor<'button', 'onClick'>) {
    event.stopPropagation();

    if (!isLoggedIn) {
      push('/login');
      return;
    }

    setIsLoading(true);

    const json = await postBookmark(placeId, placeBookmarked);

    const result = postSpotResponseSchema.safeParse(json);

    if (!result.success) {
      console.log(result.error.message);
      setIsLoading(false);
    }

    setPlaceBookmarked(!placeBookmarked);
    setIsLoading(false);
  }

  return (
    <button
      className={`${className} p-1 border-0 bg-transparent`}
      onClick={handleClick}
      disabled={isLoading}
    >
      {isLoading ? (
        <Spinner size={`${iconSize ? iconSize : 'w-12 h-12'}`} />
      ) : (
        <BookmarkIcon
          className={`stroke-[3px] ${iconSize ? iconSize : 'w-12 h-12'} ${
            placeBookmarked
              ? 'fill-main stroke-main'
              : 'fill-none stroke-[#9CA3AF]'
          }`}
        />
      )}
    </button>
  );
}
