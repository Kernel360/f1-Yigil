'use client';
import React, { useRef } from 'react';
import { Marker, NaverMap, useNavermaps } from 'react-naver-maps';

export default function ViewTravelMap() {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);
  const markerRef = useRef<naver.maps.Marker | null>(null);

  return (
    <NaverMap
      center={{
        lat: 37.5135869,
        lng: 127.0621708,
      }}
      zoom={15}
      ref={mapRef}
      onZoomChanged={() => console.log(mapRef.current?.getBounds())}
    >
      {/* {place && (
        <Marker
          ref={markerRef}
          title={place.name}
          position={place.coords}
          icon={plusMarker(place)}
          onClick={() => handleClick(place)}
        ></Marker>
      )} */}
    </NaverMap>
  );
}
