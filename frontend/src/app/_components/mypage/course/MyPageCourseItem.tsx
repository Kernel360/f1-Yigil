import React, { useEffect, useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock-white.svg';

import Image from 'next/image';
import MapPinIcon from '/public/icons/filled-map-pin.svg';
import { TMyPageCourse } from '../types';
import IconWithCounts from '../../IconWithCounts';

interface TMyPageCourseItem extends TMyPageCourse {
  idx: number;
  checkedList: { course_id: number; isSecret: boolean }[];
  onChangeCheckedList: (id: number, isSecret: boolean) => void;
  selectOption: string;
}

export default function MyPageCourseItem({
  static_image_url,
  course_id,
  title,
  isSecret,
  created_at,
  rate,
  spot_count,
  idx,
  checkedList,
  onChangeCheckedList,
  selectOption,
}: TMyPageCourseItem) {
  const [isCheckDisabled, setIsCheckDisabled] = useState(false);
  const [isChecked, setIsChecked] = useState(false);

  useEffect(() => {
    const found = checkedList.find(
      (checked) => checked.course_id === course_id,
    );

    if (found) setIsChecked(true);
    else setIsChecked(false);
  }, [checkedList.length]);

  useEffect(() => {
    if (selectOption === 'all' && isSecret) {
      setIsCheckDisabled(true);
      setIsChecked(false);
    }
  }, [selectOption, checkedList.length]); // 전체 선택 및 해제 시에 disabled 풀리는 현상
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
        checked={isChecked}
        onChange={() => {
          onChangeCheckedList(course_id, isSecret);
        }}
      />
      <div className="flex flex-col gap-y-2 relative">
        <Image
          src={static_image_url}
          alt="course-image"
          width={360}
          height={160}
          className="w-[360px] h-[160px] rounded-md"
        />
        {isSecret && (
          <div className="absolute top-2 right-2 p-3 bg-black rounded-full">
            <LockIcon className="w-5 h-5" />
          </div>
        )}
        <div className="text-2xl leading-7 text-gray-900 font-semibold ml-2">
          {title}
        </div>
        <div className="flex gap-x-2 items-center mx-2 text-xl leading-6 text-gray-500 font-semibold">
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
            }
            count={rate}
            rating
          />
          <div className="grow text-xl leading-6 text-gray-500 font-semibold">
            <IconWithCounts
              icon={<MapPinIcon className="w-6 h-6" />}
              count={spot_count}
            />
          </div>
          <div className="text-gray-300 font-bold">{created_at}</div>
        </div>
      </div>
    </div>
  );
}
