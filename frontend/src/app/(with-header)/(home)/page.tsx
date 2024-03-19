import { homePopOverData } from '@/app/_components/ui/popover/constants';
import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PopularPlaces, RegionPlaces } from '@/app/_components/place';
import DummyPlaces from '@/app/_components/place/dummy/DummyPlaces';

import {
  getInterestedRegions,
  getPopularPlaces,
  getRegionPlaces,
} from './action';

import PlusIcon from '@/../public/icons/plus.svg';
import { myInfoSchema } from '@/types/response';
import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import RecommendedPlaces from '@/app/_components/place/RecommendedPlaces';
import { Suspense } from 'react';

function OpenedFABIcon() {
  return <PlusIcon className="w-9 h-9 rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="w-9 h-9 rotate-0 duration-200" />;
}

export default async function HomePage() {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  if (!memberInfo.success) {
    return (
      <main className="max-w-full flex flex-col gap-6 relative">
        <Suspense
          fallback={<DummyPlaces title="관심 지역" variant="primary" />}
        >
          <PopularPlaces isLoggedIn={memberInfo.success} />
        </Suspense>
        <DummyPlaces title="관심 지역" variant="secondary" />
        <DummyPlaces title="맞춤 추천" variant="secondary" />
        <FloatingActionButton
          popOverData={homePopOverData}
          backdropStyle="bg-black bg-opacity-10"
          openedIcon={<OpenedFABIcon />}
          closedIcon={<ClosedFABIcon />}
        />
      </main>
    );
  }

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
          <PopularPlaces isLoggedIn={memberInfo.success} />
        </Suspense>
        <RegionPlaces
          regions={regions}
          initialRegionPlaces={[]}
          isLoggedIn={memberInfo.success}
          variant="secondary"
          carousel
        />
        <FloatingActionButton
          popOverData={homePopOverData}
          backdropStyle="bg-black bg-opacity-10"
          openedIcon={<OpenedFABIcon />}
          closedIcon={<ClosedFABIcon />}
        />
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
        <PopularPlaces isLoggedIn={memberInfo.success} />
      </Suspense>
      <Suspense
        fallback={<DummyPlaces title="관심 지역" variant="secondary" />}
      >
        <RegionPlaces
          regions={regions}
          initialRegion={regions[0]}
          initialRegionPlaces={regionPlaces}
          isLoggedIn={memberInfo.success}
          variant="secondary"
          carousel
        />
      </Suspense>

      <Suspense
        fallback={<DummyPlaces title="맞춤 추천" variant="secondary" />}
      >
        <RecommendedPlaces />
      </Suspense>
      <FloatingActionButton
        popOverData={homePopOverData}
        backdropStyle="bg-black bg-opacity-10"
        openedIcon={<OpenedFABIcon />}
        closedIcon={<ClosedFABIcon />}
      />
    </main>
  );
}
