import { TMapPlace } from '@/types/response';
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
}: TMapMarker) {
  const { push } = useRouter();
  const [hasManyReviews, setHasManyReviews] = useState(false);
  useEffect(() => {
    (async () => {
      // 내 아이디 바탕으로 장소 요청
      // const res = await getMySpotForPlace(id);
      // if (res.success && res.data.has_next)
      //   setHasManyReviews(res.data.has_next);
    })();
  }, [id]);

  const onClickMarker = (clickedId: number) => {
    setMarkerClickedId(clickedId);
    if (markerClickedId === clickedId) {
      push(`/place/${clickedId}`);
      return;
    } else {
      if (hasManyReviews) {
        setToastMsg('-> 를 눌러 "장소 기록"으로 이동할 수 있어요.');
      } else setToastMsg('-> 를 눌러 "장소 상세"로 이동할 수 있어요.');
      setTimeout(() => {
        setToastMsg('');
      }, 1000);
    }
  };

  const markerType = (placeName: string) => {
    if (hasManyReviews) {
      return yMarker();
    }
    if (!isClickedMarker) {
      return nearMarker(placeName);
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
