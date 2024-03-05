import { Place } from '@/app/_components/place';
import RegionSelect from '@/app/_components/place/RegionSelect';
import DummyPlace from '@/app/_components/place/dummy/DummyPlace';
import { myInfoSchema } from '@/types/response';

import { authenticateUser } from '@/app/_components/mypage/hooks/myPageActions';
import { getInterestedRegions, getPlacesOfRegion } from '../../action';

export default async function RegionsPlacePage({
  searchParams,
}: {
  searchParams: { name: string };
}) {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const interestedRegions = await getInterestedRegions();

  if (!interestedRegions.success) {
    return <main>Failed</main>;
  }

  const regions = interestedRegions.data.regions;

  if (regions.length === 0) {
    return (
      <main className="px-4 max-w-full flex flex-col">
        <h1 className="pl-4 pt-4 pb-2 text-3xl font-medium">관심 지역</h1>
        <div className="px-4 py-2">
          <RegionSelect userRegions={regions} />
        </div>
        <div className="flex flex-col gap-4 relative">
          <DummyPlace />
          <div className="absolute inset-0 bg-white/75 flex justify-center items-center"></div>
        </div>
      </main>
    );
  }

  const currentRegion = regions.find(
    ({ region_name }) => region_name === searchParams.name,
  );

  if (!currentRegion) {
    return <main>Failed</main>;
  }

  const result = await getPlacesOfRegion(currentRegion.id);

  if (!result.success) {
    console.log(result);

    return <main>Failed</main>;
  }

  const places = result.data.places;

  return (
    <main className="px-4 max-w-full flex flex-col">
      <h1 className="pl-4 pt-4 text-3xl font-medium">관심 지역</h1>
      <div className="px-4 py-2">
        <RegionSelect
          initialRegion={currentRegion}
          userRegions={interestedRegions.data.regions}
        />
      </div>
      <div className="flex flex-col gap-4 relative">
        {places.map((place, index) => (
          <Place
            key={place.id}
            data={place}
            order={index}
            isLoggedIn={memberInfo.success}
          />
        ))}
      </div>
    </main>
  );
}
