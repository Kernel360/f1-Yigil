import { TMyInfo } from '@/types/response';
import React, { Dispatch, SetStateAction, useState } from 'react';
import StarIcon from '/public/icons/star.svg';
import XIcon from '/public/icons/x.svg';

export default function SettingUserArea({
  userRegions,
  idx,
  deleteInterestedArea,
}: {
  userRegions: TMyInfo['favorite_regions'];
  idx: number;
  deleteInterestedArea: (id: number) => void;
}) {
  const [isHover, setIsHover] = useState(false);
  return idx < userRegions.length ? (
    <li
      key={userRegions[idx].id}
      onMouseEnter={() => setIsHover(true)}
      onMouseLeave={() => setIsHover(false)}
      className="relative flex justify-center items-center gap-x-2 text-xl text-main font-semibold border-[1px] border-main py-4 leading-5 rounded-md"
    >
      <div>{userRegions[idx].name}</div>
      <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
      {isHover && (
        <span
          className="absolute bg-white border-main border-[1px] rounded-full top-[-8px] right-[-8px] cursor-pointer"
          onClick={() => deleteInterestedArea(userRegions[idx].id)}
        >
          <XIcon className="w-4 h-4 stroke-black stroke-2" />
        </span>
      )}
    </li>
  ) : (
    <li
      key={idx}
      className="flex justify-center items-center gap-x-2 text-xl text-gray-300 font-semibold border-[1px] border-gray-300 py-4 leading-5 rounded-md"
    >
      <div>지역</div>
      <StarIcon className="w-4 h-4 stroke-gray-300" />
    </li>
  );
}
