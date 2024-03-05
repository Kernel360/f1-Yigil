'use client';

import Link from 'next/link';

import Places from './Places';
import RegionSelect from './RegionSelect';

import type { TPlace, TRegion } from '@/types/response';
import DummyPlaces from './dummy/DummyPlaces';
import DummyPlace from './dummy/DummyPlace';

export default function RegionPlaces({
  title,
  data,
  regions,
  isLoggedIn,
}: {
  title: string;
  data: TPlace[];
  regions: TRegion[];
  isLoggedIn: boolean;
}) {
  return (
    <section className="flex flex-col" aria-label="places">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 text-3xl font-medium">{title}</span>
        <Link href="places/regions">더보기</Link>
      </div>
      <div className="pt-4 pl-8">
        <RegionSelect initialRegion={regions[0]} userRegions={regions} />
      </div>
      {regions.length === 0 ? (
        <div className="relative flex overflow-hidden px-4">
          <DummyPlace variant="secondary" />
          <DummyPlace variant="secondary" />
          <div className="absolute inset-0 bg-white/75 flex justify-center items-center"></div>
        </div>
      ) : (
        <Places data={data} isLoggedIn={isLoggedIn} variant="secondary" />
      )}
    </section>
  );
}
