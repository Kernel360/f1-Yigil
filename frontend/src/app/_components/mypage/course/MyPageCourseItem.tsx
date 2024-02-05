import React, { useState } from 'react';
import UnLockIcon from '/public/icons/unlock.svg';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock.svg';
import { TMyPageCourse } from './MyPageCourseList';
import Image from 'next/image';
import MapPinIcon from '/public/icons/map-pin.svg';

interface TMyPageCourseItem extends TMyPageCourse {
  idx: number;
  checkedList: number[];
  filterCheckedList: (id: number) => void;
}

export default function MyPageCourseItem({
  image_url,
  course_id,
  travel_id,
  title,
  isSecret,
  post_date,
  rating,
  spots,
  idx,
  checkedList,
  filterCheckedList,
}: TMyPageCourseItem) {
  const [secret, setSecret] = useState<boolean>(isSecret);

  return (
    <div
      className={`flex items-center px-5 py-4 border-b-2 gap-x-4 ${
        idx === 0 && 'border-t-2'
      }`}
    >
      <input
        type="checkbox"
        className="w-[32px] h-[32px]"
        onChange={() => filterCheckedList(course_id)}
      />
      <div className="flex flex-col gap-y-2 relative">
        <Image
          src={image_url}
          alt="course-image"
          width={360}
          height={160}
          className="w-[360px] h-[160px] rounded-md"
        />
        <div className="text-2xl leading-7 text-gray-900 font-semibold ml-2">
          {title}
        </div>
        <div className="flex gap-x-2 items-center mx-2">
          <StarIcon className="w-4 h-4 fill-[#FBBC05]" />
          <div className="text-xl leading-6 text-gray-500 font-semibold">
            {rating.toFixed(1)}
          </div>
          <MapPinIcon className="w-6 h-6 stroke-main fill-main" />
          <div className="grow text-xl leading-6 text-gray-500 font-semibold">
            {spots}
          </div>
          <div className="text-gray-300 font-bold">
            {post_date.slice(0, 10)}
          </div>
        </div>
      </div>
    </div>
  );
}
