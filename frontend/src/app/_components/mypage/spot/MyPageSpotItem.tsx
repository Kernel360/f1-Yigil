import Image from 'next/image';
import React, { useEffect, useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock.svg';
import { TMyPageSpot } from './MyPageSpotList';

interface TMyPageSpotItem extends TMyPageSpot {
  checkedList: { postId: TMyPageSpot['postId']; isSecret: boolean }[];
  onChangeCheckedList: (id: number, isSecret: boolean) => void;
  idx: number;
  selectOption: string;
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
  onChangeCheckedList,
  idx,
  selectOption,
}: TMyPageSpotItem) {
  const [isCheckDisabled, setIsCheckDisabled] = useState(false);
  const [isChecked, setIsChecked] = useState(false);

  // TODO: 전체 선택 했을 때 isChecked가 true 로 바뀌어야 한다.
  useEffect(() => {
    const found = checkedList.find((checked) => checked.postId === postId);

    if (found) setIsChecked(true);
    else setIsChecked(false);
  }, [checkedList.length]);

  useEffect(() => {
    if (selectOption === '전체' && isSecret) {
      setIsCheckDisabled(true);
      setIsChecked(false);
    }
  }, [selectOption, checkedList.length]); // 전체 선택 및 해제 시에 disabled 풀리는 현상

  return (
    <div
      className={`flex items-center px-5 py-4 border-b-2 gap-x-4 ${
        idx === 0 && 'border-t-2'
      }`}
      aria-label="spot-item"
    >
      <input
        type="checkbox"
        disabled={isCheckDisabled}
        className="w-[32px] h-[32px]"
        checked={isChecked}
        onChange={() => {
          onChangeCheckedList(postId, isSecret);
        }}
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
          <div className="text-gray-300 font-bold">{post_date}</div>
        </div>
      </div>
    </div>
  );
}
