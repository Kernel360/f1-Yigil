import React, { useState } from 'react';
import UnLockIcon from '/public/icons/unlock.svg';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock.svg';

export interface TMyPagePlaceItem {
  post_id: number;
  travel_id: number;
  title: string;
  imageUrl: string;
  description: string;
  isSecret: boolean;
  post_date: string;
  rating: number;
}

export default function MyPageCourseItem({
  imageUrl,
  post_id,
  travel_id,
  title,
  description,
  isSecret,
  post_date,
  rating,
}: TMyPagePlaceItem) {
  const [secret, setSecret] = useState<boolean>(isSecret);

  return (
    <div
      key={travel_id}
      className="w-full rounded-md py-1 flex items-center"
      tabIndex={0}
    >
      <div className="bg-gray-200 w-[104px] h-[104px] my-3 ml-[22px] rounded-md"></div>

      <div className="flex flex-col gap-y-4 mx-4 flex-grow-[1]">
        <div className="text-gray-700 text-2xl leading-7 font-semibold">
          {title}
        </div>
        <div className="flex items-center">
          <StarIcon className="w-6 h-6" />
          <div className="text-gray-500 text-xl mx-2">{rating.toFixed(1)}</div>
          <div className="text-gray-300 ml-1 sm:ml-6 self-end">
            {post_date.slice(0, 10)}
          </div>
        </div>
      </div>
      <span
        className={`p-3 mr-[22px] ${
          secret
            ? 'border-[1px] border-gray-500 bg-gray-400'
            : 'border-[1px] border-gray-500 bg-white'
        } rounded-full cursor-pointer`}
        tabIndex={0}
        onClick={() => {
          setSecret(!secret);
        }}
        onKeyDown={(e) => {
          setSecret(e.key === 'Enter' && !secret);
        }}
      >
        {secret ? (
          <LockIcon className="w-6 h-6 animate-appear" />
        ) : (
          <UnLockIcon className="stroke-gray-500 w-6 h-6 animate-appear" />
        )}
      </span>
    </div>
  );
}
