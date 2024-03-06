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
}

export default function MapMarker({
  place_name,
  id,
  x,
  y,
  markerClickedId,
  setMarkerClickedId,
}: TMapMarker) {
  const ref = useRef<naver.maps.Marker | null>(null);
  const [isClickedMarker, setIsClickedMarker] = useState(false);
  const { push } = useRouter();

  // 내려 받는 clicked id와 비교
  // id가 -1이면 아무것도 클릭되지 않은 상태이므로 현재 클릭된 id 추가 후 isClickedMarker = true
  // id와 같다면 id로 이동
  // id와 다르다면 clikedid와 id가 같은 것만

  const onClickMarker = (clickedId: number) => {
    if (markerClickedId === -1) {
      setMarkerClickedId(clickedId);
      setIsClickedMarker(true);
      markerType(place_name);
      return;
    } else if (markerClickedId !== clickedId) {
      console.log('marker', markerClickedId);
      console.log('clickedId', clickedId, id);
      if (clickedId === id) {
        setIsClickedMarker(true);
        markerType(place_name);
        setMarkerClickedId(clickedId);
      } else if (clickedId !== id) {
        setIsClickedMarker(false);
        markerType(place_name);
      }
    } else {
      setMarkerClickedId(-1);
      setIsClickedMarker(false);
      markerType(place_name);
    }

    // if (!isClickedMarker) {
    //   if (markerClickedId === id) {
    //     setIsClickedMarker(true);
    //   }
    // } else {
    //   setIsClickedMarker(false);
    // }
  };
  console.log(id, isClickedMarker);
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
      icon={markerType(`${place_name}-${id}`)}
      onClick={() => onClickMarker(id)}
      ref={ref}
    />
  );
}

/**
 * TODO: 각 마커 클릭 기능 완료
 * 처음 클릭 했을 때 클릭한 id만 다르게 하고 나머지는 원래 상태로 만들어야 함
 * 한번 클릭했을 때 보여 줄 마커 디자인
 */
