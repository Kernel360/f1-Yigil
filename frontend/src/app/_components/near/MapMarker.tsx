import { TMapPlace } from '@/types/response';
import { useRouter } from 'next/navigation';
import React, {
  Dispatch,
  SetStateAction,
  useEffect,
  useRef,
  useState,
} from 'react';
import { Marker } from 'react-naver-maps';
import { activateMarker } from '../naver-map/markers/activateMarker';
import { basicMarker } from '../naver-map/markers/basicMarker';

interface TMapMarker extends TMapPlace {
  markerClickedId: number;
  setMarkerClickedId: Dispatch<SetStateAction<number>>;
  isClickedMarker: boolean;
}

export default function MapMarker({
  place_name,
  id,
  x,
  y,
  markerClickedId,
  setMarkerClickedId,
  isClickedMarker,
}: TMapMarker) {
  const { push } = useRouter();

  const onClickMarker = (clickedId: number) => {
    setMarkerClickedId(clickedId);
    if (markerClickedId === clickedId) {
      push(`/place/${clickedId}`);
      return;
    }
  };

  const markerType = (placeName: string) => {
    if (!isClickedMarker) {
      return basicMarker(placeName);
    } else {
      return activateMarker(placeName);
    }
  };
  return (
    <Marker
      key={id}
      title={place_name}
      position={{ x: x, y: y }}
      icon={markerType(place_name)}
      onClick={() => onClickMarker(id)}
      zIndex={markerClickedId === id ? 1 : -1}
    />
  );
}
