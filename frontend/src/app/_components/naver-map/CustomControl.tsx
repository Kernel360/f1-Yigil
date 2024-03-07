'use client';
import { Dispatch, RefObject, SetStateAction } from 'react';
import { useGeolocation } from './hooks/useGeolocation';
import CrossHairIcon from '/public/icons/crosshair.svg';

function CustomControl({
  mapRef,
  isGeolocationLoading,
  setCenter,
  setIsGelocationLoading,
}: {
  mapRef: RefObject<naver.maps.Map>;
  isGeolocationLoading: boolean;
  setCenter: Dispatch<SetStateAction<{ lat: number; lng: number }>>;
  setIsGelocationLoading: Dispatch<SetStateAction<boolean>>;
}) {
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
