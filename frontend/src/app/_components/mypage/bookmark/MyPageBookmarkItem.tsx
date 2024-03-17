import Image from 'next/image';
import React, { useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import BookmarkIcon from '/public/icons/bookmark.svg';
import IconWithCounts from '../../IconWithCounts';
import { TMyPageBookmark } from '@/types/myPageResponse';
import { postBookmark } from '../../place/action';
import ToastMsg from '../../ui/toast/ToastMsg';

export default function MyPageBookmarkItem({
  place_id,
  place_name,
  place_image,
  rate,

  idx,
}: TMyPageBookmark & { idx: number }) {
  const [isBookmarked, setIsBookmarked] = useState(true);
  const [errorText, setErrorText] = useState('');

  const onChangeBookmarkStatus = async () => {
    setIsBookmarked((prev) => !prev);
    const res = await postBookmark(place_id, isBookmarked);
    if (res.code) {
      setIsBookmarked((prev) => !prev);
      setErrorText('북마크 버튼을 다시 클릭해주세요.');
    }
    setTimeout(() => {
      setErrorText('');
    }, 1000);
  };
  return (
    <>
      <div
        className={`flex items-center px-5 py-4 border-b-2 gap-x-4 w-full ${
          idx === 0 && 'border-t-2'
        }`}
        aria-label="spot-item"
      >
        <div className="relative w-[100px] h-[100px] shrink-0">
          <Image
            src={place_image || ''}
            alt="spot-image"
            fill
            sizes="100vw"
            className="rounded-md"
          />
        </div>
        <div className="flex flex-col gap-y-2 grow">
          <div className="text-2xl leading-7 text-gray-900 font-semibold ">
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
      {errorText && <ToastMsg title={errorText} timer={1000} id={place_id} />}
    </>
  );
}
