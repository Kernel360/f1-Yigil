'use client';
import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from 'react';
import {
  Container,
  Marker,
  NaverMap,
  Polyline,
  useNavermaps,
} from 'react-naver-maps';
import XMarkIcon from '/public/icons/x-mark.svg';
import { getNumberMarker } from '../../add/course/util';
import { TLineString } from '@/context/travel/schema';

export default function CourseConfirmMap({
  lineString,
  close,
  spotAddresses,
}: {
  lineString: TLineString['coordinates'];
  close: () => void;
  spotAddresses: { lat: number; lng: number }[];
}) {
  const mapRef = useRef<naver.maps.Map>(null);
  const navermaps = useNavermaps();

  const path = lineString.map(([lng, lat]) => new navermaps.LatLng(lat, lng));

  const polylineRef = useRef<naver.maps.Polyline>(
    new naver.maps.Polyline({ path }),
  );

  useEffect(() => {
    if (mapRef.current) {
      mapRef.current.autoResize();
    }
  }, []);

  return (
    <>
      <Container
        style={{ position: 'absolute', width: '100%', height: '100%' }}
      >
        <NaverMap
          bounds={polylineRef.current.getBounds()}
          center={polylineRef.current.getBounds().getCenter()}
          ref={mapRef}
        >
          <Polyline path={polylineRef.current.getPath()} />
          {spotAddresses.map((coord, index) => (
            <Marker
              key={`${coord}-${index}`}
              position={coord}
              title={'coord'}
              icon={{
                url: getNumberMarker(index + 1),
              }}
            />
          ))}
        </NaverMap>
      </Container>
      <button
        className="absolute top-0 right-0 m-2 p-1 bg-gray-100 rounded-lg"
        onClick={close}
      >
        <XMarkIcon className="w-8 h-8 stroke-gray-500" />
      </button>
    </>
  );
}
