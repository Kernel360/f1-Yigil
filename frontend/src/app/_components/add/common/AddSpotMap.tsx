// 삭제 예정

'use client';
import React, {
  Dispatch,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import { Marker, NaverMap, useNavermaps } from 'react-naver-maps';
import { plusMarker } from '../../naver-map/markers/plusMarker';

import type { TAddSpotAction } from '../spot/SpotContext';
import { getMap } from '../common/action';
import { useGeolocation } from '../../naver-map/hooks/useGeolocation';

export default function AddSpotMap({
  place,
  dispatchStep,
  dispatchSpot,
}: {
  place?: {
    name: string;
    roadAddress: string;
    coords: { lat: number; lng: number };
  };
  dispatchStep: Dispatch<{ type: 'next' } | { type: 'previous' }>;
  dispatchSpot: Dispatch<TAddSpotAction>;
}) {
  const navermaps = useNavermaps();
  const markerRef = useRef<naver.maps.Marker | null>(null);
  const mapRef = useRef<naver.maps.Map>(null);
  const [center, setCenter] = useState<{ lat: number; lng: number }>({
    lat: 37.5135869,
    lng: 127.0621708,
  });
  const [isGeolocationLoading, setIsGeolocationLoading] = useState(false);

  const { onSuccessGeolocation, onErrorGeolocation } = useGeolocation(
    mapRef,
    setCenter,
    setIsGeolocationLoading,
  );

  useEffect(() => {
    if (!mapRef.current) {
      return;
    }

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        onSuccessGeolocation,
        onErrorGeolocation,
      );
    } else {
      setCenter({ lat: 37.5135869, lng: 127.0621708 });
    }
  }, [mapRef, onSuccessGeolocation, onErrorGeolocation]);

  useEffect(() => {
    if (mapRef.current && place) {
      const coord = { ...place.coords };
      mapRef.current.panTo(coord);
    }
  }, [place]);

  async function handleClick(place: {
    name: string;
    roadAddress: string;
    coords: { lat: number; lng: number };
  }) {
    const { name, roadAddress, coords } = place;

    dispatchSpot({ type: 'SET_NAME', payload: name });
    dispatchSpot({ type: 'SET_ADDRESS', payload: roadAddress });
    dispatchSpot({ type: 'SET_COORDS', payload: coords });

    // Image URL | Base64 DataURL
    const imageUrl = await getMap(name, roadAddress, coords);

    if (imageUrl.status === 'failed') {
      console.log('지도 이미지를 얻지 못했습니다!');
      alert('지도 이미지를 얻지 못했습니다!');
    } else {
      dispatchSpot({ type: 'SET_SPOT_MAP_URL', payload: imageUrl.data });
      dispatchStep({ type: 'next' });
    }
  }

  return (
    <NaverMap
      center={
        place
          ? new naver.maps.LatLng({ ...place?.coords })
          : new naver.maps.LatLng({ ...center })
      }
      zoom={15}
      ref={mapRef}
    >
      {place && (
        <Marker
          ref={markerRef}
          title={place.name}
          position={place.coords}
          icon={plusMarker(place.name)}
          onClick={() => handleClick(place)}
        ></Marker>
      )}
    </NaverMap>
  );
}
