import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';
import { getInterestedRegions, getRegionPlaces } from '../../../action';
import DummyPlace from '@/app/_components/place/dummy/DummyPlace';
import Link from 'next/link';
import { RegionPlaces } from '@/app/_components/place';

export default async function RegionsPlacePage({
  params,
}: {
  params: { id: number };
}) {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const interestedRegions = await getInterestedRegions();

  if (!interestedRegions.success) {
    return <main>Failed to get interested regions!</main>;
  }

  const regions = interestedRegions.data.regions;

  if (regions.length === 0) {
    return (
      <main className="px-4 max-w-full flex flex-col grow">
        <div className="relative grow">
          <DummyPlace />
          <div className="absolute inset-0 bg-white/75 flex flex-col gap-4 justify-center items-center">
            <span className="text-lg font-semibold text-center">
              관심 지역을 설정하시면
              <br />더 많은 장소를 확인할 수 있습니다.
            </span>
            <Link className="p-2 rounded-lg bg-main text-white" href="/">
              홈으로
            </Link>
          </div>
        </div>
      </main>
    );
  }

  const currentRegion = regions.find((region) => region.id == params.id);

  const regionPlacesResult = await getRegionPlaces(params.id);

  if (!regionPlacesResult.success) {
    return <main>Failed to get region places!</main>;
  }

  const places = regionPlacesResult.data.places;

  return (
    <main className="px-4 max-w-full flex flex-col">
      <RegionPlaces
        regions={regions}
        initialRegion={currentRegion || regions[0]}
        initialRegionPlaces={places}
        isLoggedIn={memberInfo.success}
        more
      />
    </main>
  );
}
