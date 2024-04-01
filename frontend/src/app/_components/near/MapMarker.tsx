import { TMapPlace, TMyInfo } from '@/types/response';
import { useRouter } from 'next/navigation';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { Marker } from 'react-naver-maps';
import { activateMarker } from '../naver-map/markers/activateMarker';
import { basicMarker } from '../naver-map/markers/basicMarker';
import { getSpots } from '@/app/(with-header)/place/[id]/@reviews/action';
import { yMarker } from '../naver-map/markers/yMarker';
import { nearMarker } from '../naver-map/markers/nearMarker';
import { getMySpotForPlace } from '../place/detail/action';

interface TMapMarker extends TMapPlace {
  markerClickedId: number;
  setMarkerClickedId: Dispatch<SetStateAction<number>>;
  isClickedMarker: boolean;
  setToastMsg: Dispatch<SetStateAction<string>>;
  isWrittenByMe: boolean;
}

export default function MapMarker({
  place_name,
  id,
  x,
  y,
  markerClickedId,
  setMarkerClickedId,
  isClickedMarker,
  setToastMsg,
  isWrittenByMe,
}: TMapMarker) {
  const { push } = useRouter();

  const onClickMarker = (clickedId: number) => {
    setMarkerClickedId(clickedId);
    if (markerClickedId === clickedId) {
      push(`/place/${clickedId}`);
      return;
    } else {
      if (isWrittenByMe) {
        setToastMsg('->를 눌러 "장소 기록"으로 이동할 수 있어요.');
      } else setToastMsg('->를 눌러 "장소 상세"로 이동할 수 있어요.');
      setTimeout(() => {
        setToastMsg('');
      }, 2000);
    }
  };

  const markerType = (placeName: string) => {
    if (isClickedMarker) return activateMarker(placeName);
    if (isWrittenByMe) {
      return yMarker();
    } else {
      return nearMarker(placeName);
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
