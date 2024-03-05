'use client';

import Link from 'next/link';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';

import type { TRegion } from '@/types/response';
import { useEffect } from 'react';

export default function RegionSelect({
  initialRegion,
  userRegions,
}: {
  initialRegion?: TRegion;
  userRegions: TRegion[];
}) {
  const { replace } = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();

  useEffect(() => {
    if (initialRegion) {
      replace(`${pathname}?${initialRegion?.region_name}`);
    }
  }, [replace, initialRegion, pathname]);

  function handleSelect(value: string) {
    const params = new URLSearchParams(searchParams);

    if (value) {
      params.set('name', value);
    } else {
      params.delete('name');
    }

    replace(`${pathname}?${params.toString()}`);
  }

  const selectedStyle = 'bg-gray-500 text-white';
  const unselectedStyle = 'bg-gray-200 text-gray-500';

  return (
    <nav className="flex items-center gap-2">
      {userRegions.length !== 0 ? (
        userRegions.map(({ id, region_name }) => (
          <button
            className={`min-w-14 px-4 py-[6px] rounded-full font-light ${
              searchParams.get('name') === region_name
                ? selectedStyle
                : unselectedStyle
            }`}
            key={id}
            onClick={() => handleSelect(region_name)}
          >
            {region_name}
          </button>
        ))
      ) : (
        <span>관심 지역을 설정하세요.</span>
      )}
      <Link className="underline text-gray-500" href="#">
        설정
      </Link>
    </nav>
  );
}
