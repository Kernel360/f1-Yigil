export const dynamic = 'force-dynamic';

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

function OpenedFABIcon() {
  return <PlusIcon className="rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="rotate-0 duration-200" />;
}

export default async function HomePage({
  searchParams,
}: {
  searchParams: { name: string };
}) {
  const popularPlacesResult = await getPopularPlaces();

  if (!popularPlacesResult.success) {
    console.log(popularPlacesResult.error.errors);

    return <main>Failed to get popular places</main>;
  }

  const popularPlaces = popularPlacesResult.data.places;

  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  if (!memberInfo.success) {
    return (
      <main className="max-w-full flex flex-col gap-4 relative">
        <PopularPlaces data={popularPlaces} isLoggedIn={memberInfo.success} />
        <DummyPlaces title="관심 지역" variant="secondary" />
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
      <main className="max-w-full flex flex-col gap-4 relative">
        <PopularPlaces data={popularPlaces} isLoggedIn={memberInfo.success} />
        <RegionPlaces
          initialRegionPlaces={[]}
          regions={regions}
          isLoggedIn={memberInfo.success}
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
    <main className="max-w-full flex flex-col gap-4 relative">
      <PopularPlaces data={popularPlaces} isLoggedIn={memberInfo.success} />
      <RegionPlaces
        initialRegionPlaces={regionPlaces}
        regions={regions}
        isLoggedIn={memberInfo.success}
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
