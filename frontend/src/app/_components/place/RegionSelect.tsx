'use client';

import { useState } from 'react';
import Link from 'next/link';

import { getRegionPlaces } from '@/app/(with-header)/(home)/action';

import type { Dispatch, SetStateAction } from 'react';
import type { TPlace, TRegion } from '@/types/response';

export default function RegionSelect({
  userRegions,
  setRegionPlaces,
}: {
  userRegions: TRegion[];
  setRegionPlaces: Dispatch<SetStateAction<TPlace[]>>;
}) {
  const [currentRegion, setCurrentRegion] = useState(userRegions[0]);

  async function handleSelect(region: TRegion) {
    setCurrentRegion(region);

    const regionPlacesResult = await getRegionPlaces(region.id);

    if (!regionPlacesResult.success) {
      console.log('Failed to get region places');
      return;
    }

    setRegionPlaces(regionPlacesResult.data.places);
  }

  if (userRegions.length === 0) {
    return (
      <nav className="flex items-center gap-2">
        <span>관심 지역을 설정하세요.</span>
        {/* 사용자 정보 수정 UI로 연결 */}
        {/* <Link className="underline text-gray-500" href="#">
          설정
        </Link> */}
      </nav>
    );
  }

  const selectedStyle = 'bg-gray-500 text-white';
  const unselectedStyle = 'bg-gray-200 text-gray-500';

  return (
    <nav className="flex items-center gap-2">
      {userRegions.map((region) => (
        <button
          className={`min-w-14 px-4 py-[6px] rounded-full font-light ${
            currentRegion.id === region.id ? selectedStyle : unselectedStyle
          }`}
          key={region.id}
          onClick={() => handleSelect(region)}
        >
          {region.name}
        </button>
      ))}
      {/* 사용자 정보 수정 UI로 연결 */}
      {/* <Link className="underline text-gray-500" href="#">
        설정
      </Link> */}
    </nav>
  );
}
