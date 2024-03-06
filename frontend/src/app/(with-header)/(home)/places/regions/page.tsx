import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { Place, RegionPlaces } from '@/app/_components/place';
import { myInfoSchema } from '@/types/response';
import { getInterestedRegions, getRegionPlaces } from '../../action';

export default async function RegionsPlacePage() {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const interestedRegions = await getInterestedRegions();

  if (!interestedRegions.success) {
    return <main>Failed to get interested regions!</main>;
  }

  const regions = interestedRegions.data.regions;

  if (regions.length === 0) {
    return (
      <main className="px-4 max-w-full flex flex-col">
        <RegionPlaces
          initialRegionPlaces={[]}
          regions={regions}
          isLoggedIn={memberInfo.success}
        />
      </main>
    );
  }

  const regionPlacesResult = await getRegionPlaces(regions[0].id);

  if (!regionPlacesResult.success) {
    return <main>Failed to get region places!</main>;
  }

  const places = regionPlacesResult.data.places;

  return (
    <main className="px-4 max-w-full flex flex-col">
      <div className="flex flex-col gap-4 relative">
        {places.map((place) => (
          <Place
            key={place.id}
            order={place.id}
            data={place}
            isLoggedIn={memberInfo.success}
          />
        ))}
      </div>
    </main>
  );
}
