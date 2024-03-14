import { TMyInfo } from '@/types/response';
import React, { Dispatch, SetStateAction, useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import XIcon from '/public/icons/x.svg';
import ToastMsg from '../ui/toast/ToastMsg';

export default function SettingUserArea({
  userRegions,
  idx,
  deleteInterestedArea,
}: {
  userRegions: TMyInfo['favorite_regions'];
  idx: number;
  deleteInterestedArea: (id: number) => void;
}) {
  const onClickStar = (id: number) => {
    deleteInterestedArea(id);
  };
  const onKeyDownEnter = (id: number) => {
    deleteInterestedArea(id);
  };

  return idx < userRegions.length ? (
    <li
      key={userRegions[idx].id}
      className="relative flex justify-center items-center gap-x-2 text-xl text-main font-semibold border-[1px] border-main py-3 leading-5 rounded-md"
    >
      <div className="text-center pt-[1px]">{userRegions[idx].name}</div>
      <span
        tabIndex={0}
        className="cursor-pointer"
        onClick={() => onClickStar(userRegions[idx].id)}
        onKeyDown={(e) =>
          e.key === 'Enter' && onKeyDownEnter(userRegions[idx].id)
        }
      >
        <StarIcon className="w-7 h-7 fill-[#FACC15] stroke-[#FACC15]" />
      </span>
    </li>
  ) : (
    <li
      key={idx}
      className="flex justify-center items-center gap-x-2 text-xl text-gray-300 font-semibold border-[1px] border-gray-300 py-3 leading-5 rounded-md"
    >
      <div>지역</div>
      <StarIcon className="w-7 h-7 stroke-gray-300" />
    </li>
  );
}
