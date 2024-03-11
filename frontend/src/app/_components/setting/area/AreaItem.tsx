import { TMyPageRegions } from '@/types/myPageResponse';
import React, { Dispatch, SetStateAction, useContext, useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import { EventFor } from '@/types/type';
import ToastMsg from '../../ui/toast/ToastMsg';
import { patchFavoriteRegion } from '../actions';

export default function AreaItem({
  regions,
  selectedRegions,
  setSelectedRegions,
}: {
  regions: TMyPageRegions[];
  selectedRegions: number[];
  setSelectedRegions: Dispatch<SetStateAction<number[]>>;
}) {
  const onClickStar = (e: EventFor<'li', 'onClick'>, id: number) => {
    e.stopPropagation();
    if (selectedRegions.includes(id)) {
      const deleteRegion = selectedRegions.filter((region) => region !== id);
      setSelectedRegions(deleteRegion);
      patchFavoriteRegion(deleteRegion);
    } else {
      if (selectedRegions.length === 5) {
        return;
      }
      setSelectedRegions((prev) => [...prev, id]);
      patchFavoriteRegion([...selectedRegions, id]);
    }
  };

  const onKeyDownEnter = (e: EventFor<'li', 'onKeyDown'>, id: number) => {
    if (e.key === 'Enter') {
      if (selectedRegions.includes(id)) {
        const deleteRegion = selectedRegions.filter((region) => region !== id);
        setSelectedRegions(deleteRegion);
      } else {
        setSelectedRegions((prev) => [...prev, id]);
      }
    }
  };
  return (
    <ul className="grid grid-cols-2 bg-gray-100 py-4">
      {regions.map(({ id, region_name, selected }) => (
        <li
          key={id}
          tabIndex={0}
          className="flex justify-center items-center gap-x-1 py-1 my-2 cursor-pointer"
          onClick={(e) => onClickStar(e, id)}
          onKeyDown={(e) => onKeyDownEnter(e, id)}
        >
          <div className={`text-gray-400 leading-none`}>{region_name}</div>
          <StarIcon
            className={`w-5 h-5 mb-[3px] ${
              selectedRegions.includes(id)
                ? 'fill-[#FACC15] stroke-[#FACC15]'
                : 'stroke-gray-400'
            } `}
          />
        </li>
      ))}
      {selectedRegions.length === 5 && (
        <ToastMsg title="5개 이상은 등록하실 수 없습니다." timer={1500} />
      )}
    </ul>
  );
}
