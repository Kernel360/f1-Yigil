import Image from 'next/image';
import React from 'react';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock.svg';
interface TMyPageSpotItem {
  post_id: number;
  travel_id: number;
  image_url: string;
  rating: number;
  post_date: string;
  title: string;
  description: string;
  isSecret: boolean;
  checkedList: number[];
  filterCheckedList: (id: number) => void;
  idx: number;
}

export default function MyPageSpotItem({
  post_id,
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
  const onChangeCheckList = () => {};

  return (
    <div
      className={`flex items-center px-5 py-4 border-b-2 gap-x-4 ${
        idx === 0 && 'border-t-2'
      }`}
    >
      <input
        type="checkbox"
        className="w-[32px] h-[32px]"
        onChange={() => filterCheckedList(post_id)}
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
