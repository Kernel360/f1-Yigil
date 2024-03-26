import Link from 'next/link';

import type { TMySpotForPlace } from '@/types/response';
import MySpot from './MySpot';

export default function PlaceMySpot({
  placeName,
  placeAddress,
  mapImageUrl,
  coords,
  data,
}: {
  placeName: string;
  placeAddress: string;
  mapImageUrl: string;
  coords: { lat: number; lng: number };
  data: TMySpotForPlace;
}) {
  if (!data.exists) {
    const { lat, lng } = coords;
    const queryParams = Object.entries({
      name: placeName,
      address: placeAddress,
      mapImageUrl: mapImageUrl,
      lat,
      lng,
    })
      .map(([key, value]) => `${key}=${value}`)
      .join('&');

    return (
      <section className="px-4 py-2 flex flex-col gap-4 justify-center">
        <p className="px-2 text-xl text-medium text-gray-500">
          나의 기록이 아직 없습니다.
        </p>
        <Link
          className="py-2 w-full bg-[#3B82F6] rounded-md text-xl text-white flex justify-center items-center "
          href={`/add/spot?${queryParams}`}
        >
          장소 기록하기
        </Link>
      </section>
    );
  }

  return <MySpot placeName={placeName} data={data} />;
}
