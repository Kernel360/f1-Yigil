import Image from 'next/image';
import React, { useEffect, useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock-white.svg';
import IconWithCounts from '../../IconWithCounts';
import { TMyPageSpot } from '@/types/myPageResponse';

interface TMyPageSpotItem extends TMyPageSpot {
  checkedList: { spot_id: TMyPageSpot['spot_id']; is_private: boolean }[];
  onChangeCheckedList: (id: number, is_private: boolean) => void;
  idx: number;
  selectOption: string;
}

const MyPageSpotItem = ({
  spot_id,

  image_url,
  rate,
  created_date,
  title,

  is_private,
  checkedList,
  onChangeCheckedList,
  idx,
  selectOption,
}: TMyPageSpotItem) => {
  const [isCheckDisabled, setIsCheckDisabled] = useState(false);
  const [isChecked, setIsChecked] = useState(false);

  // TODO: 전체 선택 했을 때 isChecked가 true 로 바뀌어야 한다.
  useEffect(() => {
    const found = checkedList.find((checked) => checked.spot_id === spot_id);

    if (found) setIsChecked(true);
    else setIsChecked(false);
  }, [checkedList, spot_id]);

  useEffect(() => {
    if (selectOption === 'all' && is_private) {
      setIsCheckDisabled(true);
      setIsChecked(false);
    }
  }, [selectOption, checkedList, is_private]); // 전체 선택 및 해제 시에 disabled 풀리는 현상

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
          onChangeCheckedList(spot_id, is_private);
        }}
      />
      <div className="relative">
        <Image
          src={image_url || ''}
          alt="spot-image"
          width={100}
          height={100}
          className="w-[100px] h-[100px] rounded-md"
        />
        {is_private && (
          <div className="absolute top-2 right-2 p-2 bg-black rounded-full">
            <LockIcon className="w-4 h-4" />
          </div>
        )}
      </div>

      <div className="flex flex-col gap-y-2 grow">
        <div className="text-2xl leading-7 text-gray-900 font-semibold">
          {title}
        </div>
        <div className="flex gap-x-2 items-center justify-between ml-1 text-xl leading-6 text-gray-500 font-semibold">
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
            }
            count={rate}
            rating
          />
          <div className="text-gray-300 font-bold">{created_date}</div>
        </div>
      </div>
    </div>
  );
};

export default MyPageSpotItem;
