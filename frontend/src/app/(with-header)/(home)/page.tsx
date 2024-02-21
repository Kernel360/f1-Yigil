import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PlaceList } from '@/app/_components/place';
import { placeData } from '@/app/_components/place/constants';
import { homePopOverData } from '@/app/_components/ui/popover/constants';

import PlusIcon from '@/../public/icons/plus.svg';

function OpenedFABIcon() {
  return <PlusIcon className="rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="rotate-0 duration-200" />;
}

export default async function HomePage() {
  return (
    <main className="max-w-full flex flex-col gap-4 relative">
      <PlaceList title="인기" variant="primary" data={placeData} />
      <PlaceList title="관심 지역" variant="secondary" data={placeData} />
      <FloatingActionButton
        popOverData={homePopOverData}
        backdropStyle="bg-black bg-opacity-10"
        openedIcon={<OpenedFABIcon />}
        closedIcon={<ClosedFABIcon />}
      />
    </main>
  );
}
