'use client';
import { TMyPageAllArea } from '@/types/myPageResponse';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import ChevronDown from '/public/icons/chevron-down.svg';
import AreaItem from './AreaItem';

export default function AreaList({ categories }: TMyPageAllArea) {
  const counts = useMemo(() => {
    return categories.flatMap((category) =>
      category.regions.filter((region) => region.selected),
    ).length;
  }, [categories]);

  const [clickedRegion, setClickedRegion] = useState('');

  const changeDropdownRegion = (regionName: string) => {
    setClickedRegion(regionName);
    if (clickedRegion === regionName) setClickedRegion('');
  };

  const onKeyDownEnter = (regionName: string) => {
    setClickedRegion(regionName);
    if (clickedRegion === regionName) setClickedRegion('');
  };

  return (
    <section className="mx-6">
      <div className="flex justify-end items-center text-gray-400">
        <div className="text-2xl leading-7 mr-1">{counts} /</div>
        <div className="text-xl leading-6">5</div>
      </div>
      <ul className={`flex flex-wrap justify-between mt-3`}>
        {categories.map(({ category_name, regions }, idx) => (
          <>
            <li
              tabIndex={0}
              key={category_name}
              className={`cursor-pointer basis-[47%] mt-6 order-${idx} shrink-0`}
              onKeyDown={(e) =>
                e.key === 'Enter' && onKeyDownEnter(category_name)
              }
              onClick={() => changeDropdownRegion(category_name)}
            >
              <ul
                className={`flex justify-between items-center px-2 py-3 relative ${
                  clickedRegion === category_name
                    ? 'bg-gray-100 border-b-2 border-b-gray-100'
                    : 'border-b-2 border-b-gray-500'
                }`}
              >
                <div>{category_name}</div>
                {clickedRegion === category_name ? (
                  <ChevronDown className="w-4 h-4 stroke-gray-500 rotate-180" />
                ) : (
                  <ChevronDown className="w-4 h-4 stroke-gray-500" />
                )}
              </ul>
            </li>
            {clickedRegion === category_name && (
              <div className={`w-full ${idx % 2 === 0 && `order-${idx + 2}`}`}>
                <AreaItem regions={regions} />
              </div>
            )}
          </>
        ))}
      </ul>
    </section>
  );
}
