'use client';

import { useContext, useRef, useState } from 'react';
import { NaverMap, useNavermaps } from 'react-naver-maps';

import AddTravelSearchBar from '../AddTravelSearchBar';
import { PlaceContext } from '@/context/travel/place/PlaceContext';
import { isDefaultPlace } from '@/context/travel/place/reducer';
import MapComponent from '../../naver-map/MapComponent';

export default function AddSpotMap() {
  const navermaps = useNavermaps();
  const [state, dispatch] = useContext(PlaceContext);
  const mapRef = useRef<naver.maps.Map>(null);

  const [center, setCenter] = useState<{ lat: number; lng: number }>({
    lat: 37.5135869,
    lng: 127.0621708,
  });

  if (state.type === 'course') {
    return null;
  }

  return (
    <section className="flex flex-col grow">
      <hr className="my-4" />
      <section className="grow">
        <MapComponent width="100%" height="100%">
          <div className="absolute">
            <NaverMap
              center={
                isDefaultPlace(state)
                  ? new naver.maps.LatLng({ ...center })
                  : new naver.maps.LatLng({ ...state.data.coords })
              }
              zoom={15}
              ref={mapRef}
            ></NaverMap>
          </div>
        </MapComponent>
      </section>
    </section>
  );
}
