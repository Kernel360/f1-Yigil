import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { RegionPlaces } from '@/app/_components/place';
import { myInfoSchema } from '@/types/response';
import { getInterestedRegions } from '../../action';

export default async function RegionsPlacePage() {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const interestedRegions = await getInterestedRegions();

  if (!interestedRegions.success) {
    return <main>Failed</main>;
  }

  const regions = interestedRegions.data.regions;

  return (
    <main className="px-4 max-w-full flex flex-col">
      <RegionPlaces regions={regions} isLoggedIn={memberInfo.success} />
    </main>
  );
}
