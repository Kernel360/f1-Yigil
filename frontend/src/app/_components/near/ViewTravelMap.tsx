'use client';
import { TMapPlace } from '@/types/response';
import React, {
  MutableRefObject,
  RefObject,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import { NaverMap, useNavermaps } from 'react-naver-maps';
import CustomControl from '../naver-map/CustomControl';
import { useGeolocation } from '../naver-map/hooks/useGeolocation';
import { getNearPlaces } from './hooks/nearActions';
import MapPagination from '../naver-map/MapPagination';
import MapMarker from './MapMarker';
import LoadingIndicator from '../LoadingIndicator';
import ToastMsg from '../ui/toast/ToastMsg';

export default function ViewTravelMap() {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [center, setCenter] = useState<{ lat: number; lng: number }>({
    lat: 37.5135869,
    lng: 127.0621708,
  });
  const [maxMinBounds, setMaxMinBounds] = useState({
    maxX: 0,
    maxY: 0,
    minX: 0,
    minY: 0,
  });

  const [allPlaces, setAllPlaces] = useState<TMapPlace[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [markerClickedId, setMarkerClickedId] = useState(-1);
  const [isLoading, setIsLoading] = useState(false);
  const [isGeolocationLoading, setIsGeolocationLoading] = useState(true);
  const { onSuccessGeolocation, onErrorGeolocation } = useGeolocation(
    mapRef,
    setCenter,
    setIsGeolocationLoading,
  );

  useEffect(() => {
    if (!mapRef.current) {
      return;
    }

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        onSuccessGeolocation,
        onErrorGeolocation,
      );
    } else {
      setCenter({ lat: 37.5135869, lng: 127.0621708 });
    }
  }, [mapRef, onSuccessGeolocation, onErrorGeolocation]);

  useEffect(() => {
    if (mapRef.current) {
      const { x: maxX, y: maxY } = mapRef.current.getBounds().getMax();
      const { x: minX, y: minY } = mapRef.current.getBounds().getMin();
      setMaxMinBounds((v) => ({ ...v, maxX, maxY, minX, minY }));
    }
  }, []);

  const onChangedMap = () => {
    if (!mapRef.current) return;
    const { x: maxX, y: maxY } = mapRef.current.getBounds().getMax();
    const { x: minX, y: minY } = mapRef.current.getBounds().getMin();
    const { x: lng, y: lat } = mapRef.current.getCenter();
    setMaxMinBounds({ ...maxMinBounds, maxX, maxY, minX, minY });
    setCenter({ lat, lng });
    setMarkerClickedId(-1);
  };

  const getPlaces = useCallback(async () => {
    setMarkerClickedId(-1);
    try {
      const placeList = await getNearPlaces(maxMinBounds, currentPage);
      if (!placeList.success) {
        alert('장소 데이터를 불러오는데 실패했습니다');
        return;
      }
      setAllPlaces(placeList.data.places);
      setTotalPages(placeList.data.total_pages);
    } catch (error) {
      console.log(error);
    } finally {
      setIsLoading(false);
    }
  }, [currentPage, maxMinBounds]);

  const timer = useRef<null | NodeJS.Timeout>(null);
  useEffect(() => {
    timer.current = setTimeout(() => {
      setIsLoading(true);
      getPlaces();
    }, 500);

    return () => {
      if (timer.current) {
        clearTimeout(timer.current);
        timer.current = null;
      }
    };
  }, [getPlaces]);

  return (
    <>
      <CustomControl
        mapRef={mapRef}
        setCenter={setCenter}
        isGeolocationLoading={isGeolocationLoading}
        setIsGelocationLoading={setIsGeolocationLoading}
      />
      <NaverMap
        center={center}
        zoom={15}
        ref={mapRef}
        onSizeChanged={onChangedMap}
        onBoundsChanged={onChangedMap}
      >
        {isGeolocationLoading || isLoading ? (
          <div className="relative w-full h-full flex items-center justify-center">
            <LoadingIndicator
              backgroundColor="bg-white"
              loadingText={
                isGeolocationLoading
                  ? '내 위치 불러오는중...'
                  : isLoading
                  ? '장소 정보 불러오는중...'
                  : ''
              }
            />
          </div>
        ) : (
          allPlaces.map((place) => (
            <MapMarker
              key={place.id}
              {...place}
              markerClickedId={markerClickedId}
              setMarkerClickedId={setMarkerClickedId}
              isClickedMarker={
                place.id !== markerClickedId || markerClickedId === -1
                  ? false
                  : true
              }
            />
          ))
        )}
      </NaverMap>
      {markerClickedId > 0 && (
        <ToastMsg
          title="한번 더 클릭하면 장소 상세페이지로 이동합니다"
          timer={2000}
          id={markerClickedId}
        />
      )}

      {!allPlaces.length && !isLoading && (
        <ToastMsg title="주변 장소가 없습니다" timer={2000} />
      )}

      {totalPages && (
        <MapPagination
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalPage={totalPages}
        />
      )}
    </>
  );
}
