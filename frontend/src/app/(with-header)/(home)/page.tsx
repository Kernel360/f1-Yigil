export const dynamic = 'force-dynamic';

import { homePopOverData } from '@/app/_components/ui/popover/constants';
import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PopularPlaces, RegionPlaces } from '@/app/_components/place';
import DummyPlaces from '@/app/_components/place/dummy/DummyPlaces';

import { authenticateUser } from '@/app/_components/mypage/hooks/myPageActions';
import { getInterestedRegions, getPopularPlaces } from './action';

import { myInfoSchema } from '@/types/response';

import PlusIcon from '@/../public/icons/plus.svg';

function OpenedFABIcon() {
  return <PlusIcon className="rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="rotate-0 duration-200" />;
}

export default async function HomePage() {
  const result = await getPopularPlaces();

  if (!result.success) {
    console.log(result);

    return <main>Failed to get popular places</main>;
  }

  const places = result.data.places;

  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  if (!memberInfo.success) {
    return (
      <main className="max-w-full flex flex-col gap-4 relative">
        <PopularPlaces
          title="인기"
          data={places}
          isLoggedIn={memberInfo.success}
        />
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
    return <main>Failed</main>;
  }

  const regions = interestedRegions.data.regions;

  return (
    <main className="max-w-full flex flex-col gap-4 relative">
      <PopularPlaces
        title="인기"
        data={places}
        isLoggedIn={memberInfo.success}
      />
      <RegionPlaces
        title="관심 지역"
        data={places}
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
