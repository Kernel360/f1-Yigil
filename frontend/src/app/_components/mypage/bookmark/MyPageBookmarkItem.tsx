import Image from 'next/image';
import React, { useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import BookmarkIcon from '/public/icons/bookmark.svg';
import { TMyPageBookmark } from '../types';
import IconWithCounts from '../../IconWithCounts';
import { addMyPageBookmark, deleteMyPageBookmark } from './bookmarkActions';

export default function MyPageBookmarkItem({
  place_id,
  place_name,
  place_image,
  rate,

  idx,
}: TMyPageBookmark & { idx: number }) {
  const [isBookmarked, setIsBookmarked] = useState(true);
  const onChangeBookmarkStatus = () => {
    setIsBookmarked(!isBookmarked);
    // api 디바운싱 추가 필요
    if (isBookmarked) {
      deleteMyPageBookmark(place_id);
    } else {
      addMyPageBookmark(place_id);
    }
  };
  return (
    <div
      className={`flex items-center px-5 py-4 border-b-2 gap-x-4 w-full ${
        idx === 0 && 'border-t-2'
      }`}
      aria-label="spot-item"
    >
      <Image
        src={place_image || ''}
        alt="spot-image"
        width={100}
        height={100}
        className="w-[100px] h-[100px] rounded-md"
      />
      <div className="flex flex-col gap-y-2 grow w-fit">
        <div className="text-2xl leading-7 text-gray-900 w-fit font-semibold whitespace-wrap overflow-hidden text-ellipsis">
          {place_name}
        </div>
        <div className="flex gap-x-2 ml-1 items-center text-xl leading-6 text-gray-500 font-semibold">
          <IconWithCounts
            icon={<StarIcon className="w-4 h-4 fill-[#FBBC05]" />}
            count={rate}
            rating
          />
        </div>
      </div>
      <div>
        <button onClick={onChangeBookmarkStatus}>
          <BookmarkIcon
            className={`w-5 h-6 ${
              isBookmarked
                ? 'fill-main stroke-main'
                : 'fill-none stroke-gray-300 stroke-[4px]'
            }`}
          />
        </button>
      </div>
    </div>
  );
}
