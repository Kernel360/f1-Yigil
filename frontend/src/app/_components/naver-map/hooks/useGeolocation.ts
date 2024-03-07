import { Dispatch, RefObject, SetStateAction, useCallback } from 'react';
import { useNavermaps } from 'react-naver-maps';

export const useGeolocation = (
  mapRef: RefObject<naver.maps.Map>,
  setCenter: Dispatch<SetStateAction<{ lat: number; lng: number }>>,
  setIsGelocationLoading: Dispatch<SetStateAction<boolean>>,
) => {
  const navermaps = useNavermaps();

  const onSuccessGeolocation = useCallback(
    (position: { coords: { latitude: number; longitude: number } }) => {
      if (!mapRef.current) return;
      const location = new navermaps.LatLng(
        position.coords.latitude,
        position.coords.longitude,
      );
      mapRef.current.setCenter(location);
      mapRef.current.setZoom(15);

      setCenter({
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      });
      setIsGelocationLoading(false);
    },
    [navermaps.LatLng],
  );

  const onErrorGeolocation = useCallback(() => {
    if (!mapRef.current) return;
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        onSuccessGeolocation,
        onErrorGeolocation,
      );
    } else {
      setCenter({ lat: 37.5452605, lng: 127.0526252 });
    }
    setIsGelocationLoading(false);
  }, [onSuccessGeolocation]);
  return { onSuccessGeolocation, onErrorGeolocation };
};
