import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';
import { getMySpotForPlace, getPlaceDetail } from './action';

import PlaceDetail from './PlaceDetail';
import PlaceMySpot from '../spot/PlaceMySpot';

export default async function PlaceDetailWithMySpot({
  placeId,
}: {
  placeId: number;
}) {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const detailResult = await getPlaceDetail(placeId);

  if (!detailResult.success) {
    return <main>Failed to get place detail!</main>;
  }

  const detail = detailResult.data;

  if (!memberInfo.success) {
    return (
      <section className="flex flex-col">
        <PlaceDetail detail={detail} isLoggedIn={memberInfo.success} />
      </section>
    );
  }

  const mySpotResult = await getMySpotForPlace(placeId);

  if (!mySpotResult.success) {
    return <main>Failed to get my spot for place detail!</main>;
  }

  const mySpot = mySpotResult.data;

  return (
    <section className="flex flex-col">
      <PlaceDetail detail={detail} isLoggedIn={memberInfo.success} />
      <hr className="border-8" />
      <PlaceMySpot placeName={detail.place_name} data={mySpot} />
    </section>
  );
}
