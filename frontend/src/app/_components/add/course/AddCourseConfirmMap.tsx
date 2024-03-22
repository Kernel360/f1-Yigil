'use client';

import { useContext, useEffect, useRef } from 'react';
import {
  Container,
  Marker,
  NaverMap,
  Polyline,
  useNavermaps,
} from 'react-naver-maps';

import { CourseContext } from '@/context/travel/course/CourseContext';
import { getNumberMarker } from './util';

import XMarkIcon from '/public/icons/x-mark.svg';

function getPath(positions: number[][]) {
  return positions.map(([lng, lat]) => new naver.maps.LatLng(lat, lng));
}

export default function AddCourseConfirmMap({ close }: { close: () => void }) {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);

  const [course] = useContext(CourseContext);

  const spots = course.spots;
  const path = getPath(course.lineString.coordinates);

  const polylineRef = useRef<naver.maps.Polyline>(
    new naver.maps.Polyline({ path }),
  );

  useEffect(() => {
    if (mapRef.current) {
      console.log(
        polylineRef.current.getBounds().intersects(mapRef.current?.getBounds()),
      );
    }
  }, []);

  return (
    <section className="absolute w-full h-full bg-white flex flex-col grow">
      <Container
        style={{ position: 'absolute', width: '100%', height: '100%' }}
      >
        <NaverMap
          bounds={polylineRef.current.getBounds()}
          center={polylineRef.current.getBounds().getCenter()}
          ref={mapRef}
        >
          <Polyline path={polylineRef.current.getPath()} />
          {spots.map(({ place: { name, address, coords } }, index) => (
            <Marker
              key={`${name}-${address}`}
              position={coords}
              title={name}
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
    </section>
  );
}
