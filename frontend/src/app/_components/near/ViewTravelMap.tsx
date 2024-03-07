'use client';
import { TMapPlace } from '@/types/response';
import React, { useEffect, useRef, useState } from 'react';
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
  const [isLoading, setIsLoading] = useState(true);
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
      setMaxMinBounds({ ...maxMinBounds, maxX, maxY, minX, minY });
    }
  }, []);

  const onChangedMap = () => {
    if (!mapRef.current) return;
    const { x: maxX, y: maxY } = mapRef.current.getBounds().getMax();
    const { x: minX, y: minY } = mapRef.current.getBounds().getMin();
    const { x: lng, y: lat } = mapRef.current.getCenter();
    setMaxMinBounds({ ...maxMinBounds, maxX, maxY, minX, minY });
    setCenter({ lat, lng });
  };

  let timer: NodeJS.Timeout;
  useEffect(() => {
    timer = setTimeout(() => {
      setIsLoading(true);
      getPlaces();
    }, 500);
    return () => clearTimeout(timer);
  }, [center, currentPage, maxMinBounds]);

  const getPlaces = async () => {
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
  };

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
        onSizeChanged={() => console.log(mapRef.current?.getBounds())}
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
