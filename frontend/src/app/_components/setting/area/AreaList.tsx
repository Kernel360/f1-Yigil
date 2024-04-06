'use client';
import { TMyPageAllArea } from '@/types/myPageResponse';
import React, { useEffect, useState } from 'react';
import ChevronDown from '/public/icons/chevron-down.svg';
import AreaItem from './AreaItem';

export default function AreaList({ categories }: TMyPageAllArea) {
  const [clickedRegion, setClickedRegion] = useState('');
  const [selectedRegions, setSelectedRegions] = useState<number[]>([]);

  useEffect(() => {
    const regionIds = categories
      .flatMap((category) =>
        category.regions.filter((region) => {
          if (region.selected) return region.id;
        }),
      )
      .map((region) => region.id);
    setSelectedRegions(regionIds);
  }, [categories]);
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
        <div className="text-2xl leading-7 mr-1">
          {selectedRegions.length} /
        </div>
        <div className="text-xl leading-6">5</div>
      </div>
      <div className="mt-3">
        <table className="w-full">
          <tbody>
            {categories.map(({ category_name, regions }, idx) => (
              <React.Fragment key={category_name}>
                {idx % 2 === 0 && (
                  <tr className="flex gap-x-6">
                    <td
                      tabIndex={0}
                      className="cursor-pointer basis-[48%]"
                      onKeyDown={(e) =>
                        e.key === 'Enter' && onKeyDownEnter(category_name)
                      }
                      onClick={() => changeDropdownRegion(category_name)}
                    >
                      <div
                        className={`flex justify-between items-center px-2 py-3 mt-4  ${
                          clickedRegion === category_name
                            ? 'bg-gray-100 border-b-[1px] border-b-gray-100'
                            : 'border-b-[1px] border-b-gray-500'
                        }`}
                      >
                        <div>{category_name}</div>
                        {clickedRegion === category_name ? (
                          <ChevronDown className="w-4 h-4 stroke-gray-500 rotate-180" />
                        ) : (
                          <ChevronDown className="w-4 h-4 stroke-gray-500" />
                        )}
                      </div>
                    </td>
                    {categories[idx + 1] && (
                      <td
                        tabIndex={0}
                        className="cursor-pointer basis-[48%]"
                        onKeyDown={(e) =>
                          e.key === 'Enter' &&
                          onKeyDownEnter(categories[idx + 1].category_name)
                        }
                        onClick={() =>
                          changeDropdownRegion(
                            categories[idx + 1].category_name,
                          )
                        }
                      >
                        <div
                          className={`flex justify-between items-center px-2 py-3 mt-4 ${
                            clickedRegion === categories[idx + 1].category_name
                              ? 'bg-gray-100 border-b-[1px] border-b-gray-100'
                              : 'border-b-[1px] border-b-gray-500'
                          } `}
                        >
                          <div>{categories[idx + 1].category_name}</div>
                          {clickedRegion ===
                          categories[idx + 1].category_name ? (
                            <ChevronDown className="w-4 h-4 stroke-gray-500 rotate-180" />
                          ) : (
                            <ChevronDown className="w-4 h-4 stroke-gray-500" />
                          )}
                        </div>
                      </td>
                    )}
                  </tr>
                )}
                {clickedRegion === category_name && (
                  <tr>
                    <td colSpan={2}>
                      <div className="w-full">
                        <AreaItem
                          regions={regions}
                          selectedRegions={selectedRegions}
                          setSelectedRegions={setSelectedRegions}
                        />
                      </div>
                    </td>
                  </tr>
                )}
              </React.Fragment>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  );
}

/**
 *
 */

/**
 * {/*
 **/
