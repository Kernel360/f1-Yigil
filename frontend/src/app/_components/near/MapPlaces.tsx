import { TMapPlace } from '@/types/response';
import React, {
  MutableRefObject,
  RefObject,
  useEffect,
  useRef,
  useState,
} from 'react';
import { Listener, Marker, useNavermaps } from 'react-naver-maps';
import { basicMarker } from '../naver-map/markers/basicMarker';

export default function MapPlaces({
  allPlaces,
  totalPages,
}: {
  allPlaces: TMapPlace[];
  totalPages: number;
}) {
  const markerRefs: Array<RefObject<naver.maps.Marker>> = [];
  const navermaps = useNavermaps();
  const onClickBasicMarker = (id: number) => {
    console.log('clicked', id);
  };

  //
  const onClickMarker = (id: number) => {};

  useEffect(() => {
    markerRefs = markerRefs.current.slice(0, allPlaces.length);
    console.log(markerRefs.current);
  }, [markerRefs.current]);
  return (
    <>
      {!!allPlaces.length &&
        allPlaces.map((place, idx) => {
          return (
            
          );
        })}
    </>
  );
}
