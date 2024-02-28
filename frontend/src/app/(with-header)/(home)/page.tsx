export const dynamic = 'force-dynamic';

import { cookies } from 'next/headers';

import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PlaceList } from '@/app/_components/place';
import { homePopOverData } from '@/app/_components/ui/popover/constants';

import PlusIcon from '@/../public/icons/plus.svg';
import { getPopularPlaces } from './action';

function OpenedFABIcon() {
  return <PlusIcon className="rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="rotate-0 duration-200" />;
}

export default async function HomePage() {
  const cookie = cookies().get('SESSION');

  const result = await getPopularPlaces();

  // response parse 실패
  // ErrorBoundary 또는 Suspense 검토 가능
  if (!result.success) {
    console.log(result);

    return <main>Failed</main>;
  }

  const places = result.data.places;

  // console.log(places);

  return (
    <main className="max-w-full flex flex-col gap-4 relative">
      <PlaceList
        title="인기"
        variant="primary"
        data={places}
        isLoggedIn={cookie !== undefined}
      />
      <PlaceList
        title="관심 지역"
        variant="secondary"
        data={places}
        isLoggedIn={cookie != undefined}
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
