import Image from 'next/image';
import React, { useEffect, useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock.svg';
import { TMyPageSpot } from './MyPageSpotList';

interface TMyPageSpotItem extends TMyPageSpot {
  checkedList: { postId: TMyPageSpot['postId']; isSecret: boolean }[];
  filterCheckedList: (id: number, isSecret: boolean) => void;
  idx: number;
}

export default function MyPageSpotItem({
  postId,
  travel_id,
  image_url,
  rating,
  post_date,
  title,
  description,
  isSecret,
  checkedList,
  filterCheckedList,
  idx,
}: TMyPageSpotItem) {
  const [isCheckDisabled, setIsCheckDisabled] = useState(false);

  // checkList의 첫 번째 체크 아이템이 잠금 아이템이라면 잠그지 않은 아이템 disabled 처리
  useEffect(() => {
    const firstItem = checkedList[0];
    if (firstItem?.isSecret === true && isSecret === false)
      setIsCheckDisabled(true);
    else if (firstItem?.isSecret === false && isSecret === true)
      setIsCheckDisabled(true);
    else if (!checkedList.length) {
      setIsCheckDisabled(false);
    }
  }, [checkedList.length]);

  return (
    <div
      className={`flex items-center px-5 py-4 border-b-2 gap-x-4 ${
        idx === 0 && 'border-t-2'
      }`}
    >
      <input
        type="checkbox"
        disabled={isCheckDisabled}
        className="w-[32px] h-[32px]"
        onChange={() => filterCheckedList(postId, isSecret)}
      />
      <div className="relative">
        <Image
          src={image_url}
          alt="spot-image"
          width={100}
          height={100}
          className="w-[100px] h-[100px] rounded-md"
        />
        {isSecret && (
          <div className="absolute top-2 right-2 p-2 bg-black rounded-full">
            <LockIcon className="w-[16px] h-[16px] stroke-white" />
          </div>
        )}
      </div>

      <div className="flex flex-col gap-y-2 grow">
        <div className="text-2xl leading-7 text-gray-900 font-semibold">
          {title}
        </div>
        <div className="flex gap-x-2 items-center">
          <StarIcon className="w-4 h-4 fill-[#FBBC05]" />
          <div className="grow text-xl leading-6 text-gray-500 font-semibold">
            {rating.toFixed(1)}
          </div>
          <div className="text-gray-300 font-bold">
            {post_date.slice(0, 10)}
          </div>
        </div>
      </div>
    </div>
  );
}
