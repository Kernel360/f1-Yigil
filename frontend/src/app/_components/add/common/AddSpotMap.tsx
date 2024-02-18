'use client';
import React, { Dispatch, useRef } from 'react';
import { Listener, NaverMap, Overlay, useNavermaps } from 'react-naver-maps';

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
  const info = useRef<any>(null);

  // 이렇게 해도 장소 선택이 바뀔 때마다 마커가 갱신되나요?
  if (!info.current && place) {
    info.current = new navermaps.Marker({
      position: place.coords,
      icon: {
        content: `<div style=" border: 1px #60a5fa solid; border-radius: 5px; background-color: #fff;">
        <div style="display: flex; padding: 8px 10px">
        <div style="padding:0px 8px 0px 0px; color:#374151; font-size: 18px; font-weight: 600; text-align: center;">${place.name}</div>
        <div style="display:flex; justify-content: center; align-items: center;">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect width="24" height="24" rx="12" fill="#60A5FA"/>
        <path d="M12 6.16663V17.8333" stroke="white" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
        <path d="M6.16797 12H17.8346" stroke="white" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        </div>
        </div>
        <div style="position:absolute; bottom:-5px; left:50px; width: 12px; height:12px;background-color:#fff; border-bottom: 1px #60a5fa solid; border-right:1px #60a5fa solid; transform: rotate(45deg);"></div>
        </div>`,
        anchor: new navermaps.Point(50, 50),
      },
    });
  }
  const infoWindow = info.current;

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
    const { name, roadAddress, coords } = place;

    // Server Action 위치

    dispatchSpot({ type: 'SET_NAME', payload: name });
    dispatchSpot({ type: 'SET_ADDRESS', payload: roadAddress });
    dispatchSpot({ type: 'SET_COORDS', payload: coords });
    dispatchStep({ type: 'next' });
  }

  return (
    <NaverMap
      defaultCenter={new naver.maps.LatLng(37.5452605, 127.0526252)}
      zoom={15}
    >
      {place && (
        <Overlay element={infoWindow}>
          <Listener type="click" listener={() => handleClick(place)} />
        </Overlay>
      )}
    </NaverMap>
  );
}
