import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';
import { getMySpotForPlace, getPlaceDetail } from '../action';
import PlaceDetail from '@/app/_components/place/PlaceDetail';
import PlaceDetailWithMySpot from '@/app/_components/place/PlaceDetailWithMySpot';

export default async function PlaceDetailDefault({
  params,
}: {
  params: { id: number };
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
    <PlaceDetailWithMySpot
      detail={detail}
      isLoggedIn={memberInfo.success}
      mySpot={mySpot}
    />
  );
}
