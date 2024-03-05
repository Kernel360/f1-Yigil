'use client';
import {
  Dispatch,
  RefObject,
  SetStateAction,
  useEffect,
  useState,
} from 'react';
import { useNavermaps } from 'react-naver-maps';
import CrossHairIcon from '/public/icons/crosshair.svg';

function CustomControl({
  mapRef,
  center,
}: {
  mapRef: RefObject<naver.maps.Map>;
  center: { lat: number; lng: number };
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
  const [currentPosLoading, setCurrentPosLoading] = useState(false);

  const mapCurrentLocation = () => {
    if (navigator.geolocation) {
      setCurrentPosLoading(true);
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setCurrentPosLoading(false);
          const lat = position.coords.latitude;
          const lng = position.coords.longitude;
          mapRef.current?.setCenter(new navermaps.LatLng(lat, lng));
        },
        (error) => {
          setCurrentPosLoading(false);
          window.alert('현위치를 가져오는데 실패했습니다!');
          console.log(error);
        },
        { enableHighAccuracy: true },
      );
    }
  };

  return (
    <button
      className="absolute top-6 left-6 w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center"
      onClick={mapCurrentLocation}
    >
      {currentPosLoading ? (
        <div className="flex space-x-2 justify-center items-center">
          <div className="h-2.5 w-2.5 bg-gray-500 rounded-full animate-pulse"></div>
          <div className="h-2.5 w-2.5 bg-gray-500 rounded-full animate-pulse delay-150"></div>
          <div className="h-2.5 w-2.5 bg-gray-500 rounded-full animate-pulse delay-300"></div>
        </div>
      ) : (
        <CrossHairIcon />
      )}
    </button>
  );
}
export default CustomControl;
