import PlaceDetail from '@/app/_components/place/PlaceDetail';
import { myInfoSchema } from '@/types/response';
import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { getMySpotForPlace, getPlaceDetail } from '../action';
import PlaceMySpot from '@/app/_components/place/PlaceMySpot';

export default async function PlaceDetailPage({
  params,
}: {
  params: { id: string };
}) {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const detailResult = await getPlaceDetail(params.id);

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

  const mySpotResult = await getMySpotForPlace(params.id);

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
