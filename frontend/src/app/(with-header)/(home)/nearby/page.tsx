import MapComponent from '@/app/_components/naver-map/MapComponent';
import ViewTravelMap from '@/app/_components/near/ViewTravelMap';
import React from 'react';

export default async function NearbyPage() {
  
  return (
    <section className="w-full h-full">
      <MapComponent width="100%" height="100%">
        <ViewTravelMap />
      </MapComponent>
    </section>
  );
}
