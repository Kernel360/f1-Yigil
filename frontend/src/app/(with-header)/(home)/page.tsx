import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PlaceList } from '@/app/_components/place';
import { placeData } from '@/app/_components/place/constants';
import { homePopOverData } from '@/app/_components/ui/popover/constants';

export default async function HomePage() {
  return (
    <main className="max-w-full flex flex-col gap-4 relative">
      <PlaceList title="인기" variant="primary" data={placeData} />
      <PlaceList title="관심 지역" variant="secondary" data={placeData} />
      <FloatingActionButton
        popOverData={homePopOverData}
        backdropStyle="bg-black bg-opacity-10"
      />
    </main>
  );
}
