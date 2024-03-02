export const dynamic = 'force-dynamic';

import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PlaceList } from '@/app/_components/place';
import { homePopOverData } from '@/app/_components/ui/popover/constants';
import { authenticateUser } from '@/app/_components/mypage/hooks/myPageActions';
import { getPopularPlaces } from './action';

import PlusIcon from '@/../public/icons/plus.svg';
import { myInfoSchema } from '@/types/response';

function OpenedFABIcon() {
  return <PlusIcon className="rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="rotate-0 duration-200" />;
}

export default async function HomePage() {
  const memberJson = await authenticateUser();

  const memberInfo = myInfoSchema.safeParse(memberJson);

  const result = await getPopularPlaces();

  if (!result.success) {
    console.log(result);

    return <main>Failed</main>;
  }

  const places = result.data.places;

  return (
    <main className="max-w-full flex flex-col gap-4 relative">
      <PlaceList
        title="인기"
        variant="primary"
        data={places}
        isLoggedIn={memberInfo.success}
      />
      <PlaceList
        title="관심 지역"
        variant="secondary"
        data={places}
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
