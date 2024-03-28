import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import MapComponent from '@/app/_components/naver-map/MapComponent';
import ViewTravelMap from '@/app/_components/near/ViewTravelMap';
import { getMySpotIds } from '@/app/_components/near/hooks/nearActions';
import { myInfoSchema } from '@/types/response';
import React from 'react';

export default async function NearbyPage() {
  const res = await authenticateUser();
  const user = myInfoSchema.safeParse(res);

  if (!user.success) {
    return (
      <section className="w-full h-full">
        <MapComponent width="100%" height="100%">
          <ViewTravelMap myPlaces={[]} />
        </MapComponent>
      </section>
    );
  }
  const result = await getMySpotIds();
  const spotIds = result.status === 'success' && result.data.ids;
  if (spotIds) {
    return (
      <section className="w-full h-full">
        <MapComponent width="100%" height="100%">
          <ViewTravelMap myPlaces={spotIds} />
        </MapComponent>
      </section>
    );
  }
}
