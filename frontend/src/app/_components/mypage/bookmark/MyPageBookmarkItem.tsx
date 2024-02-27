import Image from 'next/image';
import React, { useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import BookmarkIcon from '/public/icons/bookmark.svg';

import { TMyPageBookmark } from '../types';
export default function MyPageBookmarkItem({
  place_id,
  place_name,
  place_image_url,
  rate,
  is_bookmarked,
  idx,
}: TMyPageBookmark & { idx: number }) {
  const [isBookmarked, setIsBookmarked] = useState(true);
  const onChangeBookmarkStatus = () => {
    setIsBookmarked(!isBookmarked);
    if (isBookmarked) {
      // deleteMyPageBookmark(place_id)
    } else {
      // add
    }
  };
  return (
    <div
      className={`flex items-center px-5 py-4 border-b-2 gap-x-4 ${
        idx === 0 && 'border-t-2'
      }`}
      aria-label="spot-item"
    >
      <div className="relative">
        <Image
          src={place_image_url}
          alt="spot-image"
          width={100}
          height={100}
          className="w-[100px] h-[100px] rounded-md"
        />
      </div>

      <div className="flex flex-col gap-y-2 grow">
        <div className="text-2xl leading-7 text-gray-900 font-semibold">
          {place_name}
        </div>
        <div className="flex gap-x-2 items-center">
          <StarIcon className="w-4 h-4 fill-[#FBBC05]" />
          <div className="grow text-xl leading-6 text-gray-500 font-semibold">
            {rate.toFixed(1)}
          </div>
        </div>
      </div>
      <div>
        <button onClick={onChangeBookmarkStatus}>
          <BookmarkIcon
            className={`${
              isBookmarked ? 'fill-main stroke-main' : 'stroke-gray-300'
            }`}
          />
        </button>
      </div>
    </div>
  );
}
