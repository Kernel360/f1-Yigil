'use client';
import React, { Dispatch, useEffect, useRef, useState } from 'react';
import {
  Listener,
  Marker,
  NaverMap,
  Overlay,
  useNavermaps,
} from 'react-naver-maps';
import { plusMarker } from '../../naver-map/plusMarker';

import type { TAddSpotAction } from '../spot/SpotContext';

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

  function onSuccessGeolocation(position: {
    coords: { latitude: number; longitude: number };
  }) {
    if (!mapRef.current) return;

    const location = new navermaps.LatLng(
      position.coords.latitude,
      position.coords.longitude,
    );
    mapRef.current.setCenter(location);
    mapRef.current.setZoom(15);

    setCenter({
      lat: position.coords.latitude,
      lng: position.coords.longitude,
    });
  }

  function onErrorGeolocation() {
    if (!mapRef.current) return;

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        onSuccessGeolocation,
        onErrorGeolocation,
      );
    } else {
      setCenter({ lat: 37.5452605, lng: 127.0526252 });
    }
  }

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
      setCenter({ lat: 37.5452605, lng: 127.0526252 });
    }
  }, [mapRef]);

  useEffect(() => {
    if (mapRef.current && place) {
      const coord = { ...place.coords };
      mapRef.current.panTo(coord);
    }
  }, []);

  /**
   * @todo static map url 얻어오는 server action 추가
   * 1. 백엔드 서버에 있는지 질의
   * 2. 없으면 네이버에 질의
   * 3. 얻어온 URL을 dispatchSpot에 제공
   */
  function handleClick(place: {
    name: string;
    roadAddress: string;
    coords: { lat: number; lng: number };
  }) {
    const { name: title, roadAddress, coords } = place;
    // Server Action 위치

    dispatchSpot({ type: 'SET_NAME', payload: name });
    dispatchSpot({ type: 'SET_ADDRESS', payload: roadAddress });
    dispatchSpot({ type: 'SET_COORDS', payload: coords });
    dispatchStep({ type: 'next' });
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
          icon={plusMarker(place)}
          onClick={() => console.log('clicked')}
        ></Marker>
      )}
    </NaverMap>
  );
}
