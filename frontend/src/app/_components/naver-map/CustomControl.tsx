'use client';
import {
  Dispatch,
  RefObject,
  SetStateAction,
  useEffect,
  useState,
} from 'react';
import { useNavermaps } from 'react-naver-maps';
import { useGeolocation } from './hooks/useGeolocation';
import CrossHairIcon from '/public/icons/crosshair.svg';

function CustomControl({
  mapRef,
  center,
  isGeolocationLoading,
  setCenter,
  setIsGelocationLoading,
}: {
  mapRef: RefObject<naver.maps.Map>;
  center: { lat: number; lng: number };
  isGeolocationLoading: boolean;
  setCenter: Dispatch<SetStateAction<{ lat: number; lng: number }>>;
  setIsGelocationLoading: Dispatch<SetStateAction<boolean>>;
}) {
  const locationBtnHtml = `
    <a href="#" 
      style="
        z-index: 100;
        overflow: hidden;
        display: inline-block;
        position: absolute;
        top: 7px;
        left: 5px;
        width: 34px;
        height: 34px;
        border: 1px solid rgba(58,70,88,.45);
        border-radius: 2px;
        background: #fcfcfd;
          background-clip: border-box;
        text-align: center;
        -webkit-background-clip: padding;
        background-clip: padding-box;
      "
    >
    </a>
  `;
  const navermaps = useNavermaps();
  const { onSuccessGeolocation, onErrorGeolocation } = useGeolocation(
    mapRef,
    setCenter,
    setIsGelocationLoading,
  );
  const mapCurrentLocation = () => {
    setIsGelocationLoading(true);
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        onSuccessGeolocation,
        onErrorGeolocation,
        { enableHighAccuracy: true },
      );
    }
  };
  return (
    <button
      className="absolute top-6 left-6 w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center"
      onClick={mapCurrentLocation}
      disabled={isGeolocationLoading}
    >
      <CrossHairIcon />
    </button>
  );
}
export default CustomControl;
