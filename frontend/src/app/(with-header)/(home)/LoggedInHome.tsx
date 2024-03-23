import { Suspense } from 'react';

import {
  PopularPlaces,
  RecommendedPlaces,
  RegionPlaces,
} from '@/app/_components/place/places';
import DummyPlaces from '@/app/_components/place/dummy/DummyPlaces';

import { getInterestedRegions, getRegionPlaces } from './action';
import HomeFAB from './HomeFAB';

/**
 * @todo RegionPlace 분리
 */
export default async function LoggedInHome() {
  const interestedRegions = await getInterestedRegions();

  if (!interestedRegions.success) {
    return <main>Failed to get regions</main>;
  }

  const regions = interestedRegions.data.regions;

  if (regions.length === 0) {
    return (
      <main className="max-w-full flex flex-col gap-6 relative">
        <Suspense
          fallback={<DummyPlaces title="관심 지역" variant="primary" />}
        >
          <PopularPlaces isLoggedIn />
        </Suspense>
        <RegionPlaces
          regions={regions}
          initialRegionPlaces={[]}
          isLoggedIn
          variant="secondary"
          carousel
        />
        <Suspense
          fallback={
            <DummyPlaces title="맞춤 추천" variant="secondary" needLogin />
          }
        >
          <RecommendedPlaces isLoggedIn />
        </Suspense>
        <HomeFAB />
      </main>
    );
  }

  const regionPlacesResult = await getRegionPlaces(regions[0].id);

  if (!regionPlacesResult.success) {
    return <main>Failed to get region places</main>;
  }

  const regionPlaces = regionPlacesResult.data.places;

  return (
    <main className="max-w-full flex flex-col gap-8 relative">
      <Suspense fallback={<DummyPlaces title="인기" variant="primary" />}>
        <PopularPlaces isLoggedIn />
      </Suspense>
      <Suspense
        fallback={<DummyPlaces title="관심 지역" variant="secondary" />}
      >
        <RegionPlaces
          regions={regions}
          initialRegion={regions[0]}
          initialRegionPlaces={regionPlaces}
          isLoggedIn
          variant="secondary"
          carousel
        />
      </Suspense>
      <Suspense
        fallback={<DummyPlaces title="맞춤 추천" variant="secondary" />}
      >
        <RecommendedPlaces isLoggedIn />
      </Suspense>
      <HomeFAB />
    </main>
  );
}
