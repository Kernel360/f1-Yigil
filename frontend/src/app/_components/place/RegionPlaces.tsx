'use client';

import { useState } from 'react';
import Link from 'next/link';

import Places from './Places';
import RegionSelect from './RegionSelect';
import DummyPlace from './dummy/DummyPlace';

import type { TPlace, TRegion } from '@/types/response';

export default function RegionPlaces({
  regions,
  initialRegionPlaces,
  isLoggedIn,
}: {
  regions: TRegion[];
  initialRegionPlaces: TPlace[];
  isLoggedIn: boolean;
}) {
  const [regionPlaces, setRegionPlaces] =
    useState<TPlace[]>(initialRegionPlaces);

  const title = '관심 지역';

  if (regions.length === 0) {
    return (
      <section className="flex flex-col" aria-label="places">
        <div className="flex justify-between items-center px-4 pt-2">
          <span className="pl-4 text-3xl font-medium">{title}</span>
          <Link href="places/regions">더보기</Link>
        </div>
        <div className="pt-4 pl-8">
          <RegionSelect
            setRegionPlaces={setRegionPlaces}
            userRegions={regions}
          />
        </div>
        <div className="relative flex overflow-hidden px-4">
          <DummyPlace variant="secondary" />
          <DummyPlace variant="secondary" />
          <div className="absolute inset-0 bg-white/75 flex justify-center items-center"></div>
        </div>
      </section>
    );
  }

  return (
    <section className="flex flex-col" aria-label="places">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 text-3xl font-medium">{title}</span>
        <Link href="places/regions">더보기</Link>
      </div>
      <div className="pt-4 pl-8">
        <RegionSelect setRegionPlaces={setRegionPlaces} userRegions={regions} />
      </div>
      <Places data={regionPlaces} isLoggedIn={isLoggedIn} variant="secondary" />
    </section>
  );
}
