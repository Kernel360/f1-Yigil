'use client';
import React, { Dispatch, useEffect, useRef, useState } from 'react';
import { Marker, NaverMap, useNavermaps } from 'react-naver-maps';
import { plusMarker } from '../../naver-map/plusMarker';

import type { TAddSpotAction } from '../spot/SpotContext';
import { getMap } from '../common/action';

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
          icon={plusMarker(place)}
          onClick={() => handleClick(place)}
        ></Marker>
      )}
    </NaverMap>
  );
}
