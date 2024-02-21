import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PlaceList } from '@/app/_components/place';
import { homePopOverData } from '@/app/_components/ui/popover/constants';

import PlusIcon from '@/../public/icons/plus.svg';
import { placesSchema } from '@/types/response';

const url =
  process.env.NODE_ENV === 'production'
    ? process.env.BASE_URL
    : 'http://localhost:8080/api/v1';

function OpenedFABIcon() {
  return <PlusIcon className="rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="rotate-0 duration-200" />;
}

export default async function HomePage() {
  const response = await fetch(`${url}/places`, {
    method: 'GET',
  });

  const body = await response.json();
  const places = placesSchema.safeParse(body);

  // response parse 실패
  // ErrorBoundary 또는 Suspense 검토 가능
  if (!places.success) {
    console.log(places.error.errors);

    return <main>Failed</main>;
  }

  return (
    <main className="max-w-full flex flex-col gap-4 relative">
      <PlaceList title="인기" variant="primary" data={places.data} />
      <PlaceList title="관심 지역" variant="secondary" data={places.data} />
      <FloatingActionButton
        popOverData={homePopOverData}
        backdropStyle="bg-black bg-opacity-10"
        openedIcon={<OpenedFABIcon />}
        closedIcon={<ClosedFABIcon />}
      />
    </main>
  );
}
