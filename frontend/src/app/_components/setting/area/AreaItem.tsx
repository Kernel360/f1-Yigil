import { TMyPageRegions } from '@/types/myPageResponse';
import React from 'react';
import StarIcon from '/public/icons/star.svg';
import { EventFor } from '@/types/type';

export default function AreaItem({ regions }: { regions: TMyPageRegions[] }) {
  const onClickStar = (e: EventFor<'li', 'onClick'>) => {
    e.stopPropagation();
  };
  return (
    <ul className="grid grid-cols-2 bg-gray-100 py-4">
      {regions.map(({ id, region_name, selected }) => (
        <li
          key={id}
          className="flex justify-center items-center gap-x-1 py-1 my-2"
          onClick={onClickStar}
        >
          <div className={`text-gray-400 leading-none`}>{region_name}</div>
          <StarIcon
            className={`w-5 h-5 mb-[3px] ${
              selected ? 'fill-[#FACC15] stroke-[#FACC15]' : 'stroke-gray-400'
            } `}
          />
        </li>
      ))}
    </ul>
  );
}
