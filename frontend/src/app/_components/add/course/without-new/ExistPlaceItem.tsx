'use client';

import Image from 'next/image';

import IconWithCounts from '../../../IconWithCounts';

import StarIcon from '/public/icons/star.svg';

export interface TExistingSpot {
  spot_id: number;
  image_url: string;
  rate: number;
  created_date: string;
  place_name: string;
}

// Check to add to Course
export default function ExistPlaceItem({
  spot,
  handleSelect,
  checked,
}: {
  spot: TExistingSpot;
  handleSelect: () => void;
  checked: boolean;
}) {
  const { place_name, image_url, rate, created_date } = spot;

  return (
    <article className="p-4 flex items-center border gap-4">
      <input
        checked={checked}
        onChange={handleSelect}
        type="checkbox"
        className="w-[32px] h-[32px] shrink-0"
      />
      <Image
        src={image_url}
        alt={`${place_name} 대표 이미지`}
        width={100}
        height={100}
        className="w-[100px] h-[100px] rounded-md"
      />
      <div className="flex flex-col gap-4 grow justify-center">
        <span className="text-2xl leading-7 text-gray-900 font-semibold text-start break-all">
          {place_name}
        </span>
        <div className="flex w-full items-center justify-between ml-1 text-xl leading-6 text-gray-500 font-semibold">
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
            }
            count={rate}
            rating
          />
          <div className="text-gray-300 font-bold">
            {new Date(created_date).toLocaleDateString('ko-KR')}
          </div>
        </div>
      </div>
    </article>
  );
}
