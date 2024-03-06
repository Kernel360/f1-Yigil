import PlaceDetail from '@/app/_components/place/PlaceDetail';
import { TMySpotForPlace, TPlaceDetail } from '@/types/response';
import PlaceMySpot from '@/app/_components/place/PlaceMySpot';

export default function PlaceDetailWithMySpot({
  detail,
  isLoggedIn,
  mySpot,
}: {
  detail: TPlaceDetail;
  isLoggedIn: boolean;
  mySpot: TMySpotForPlace;
}) {
  return (
    <section className="flex flex-col">
      <PlaceDetail detail={detail} isLoggedIn={isLoggedIn} />
      <hr className="border-8" />
      <PlaceMySpot placeName={detail.place_name} data={mySpot} />
    </section>
  );
}
