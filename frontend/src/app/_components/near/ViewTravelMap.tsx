'use client';
import { TMapPlace } from '@/types/response';
import React, { useEffect, useRef, useState } from 'react';
import {
  Listener,
  Marker,
  NaverMap,
  Overlay,
  useMap,
  useNavermaps,
} from 'react-naver-maps';
import { basicMarker } from '../naver-map/markers/basicMarker';
import CustomControl from '../naver-map/CustomControl';
import { useGeolocation } from '../naver-map/hooks/useGeolocation';
import { getNearPlaces } from './hooks/nearActions';
import MapPlaces from './MapPlaces';
import MapPagination from '../naver-map/MapPagination';
import MapMarker from './MapMarker';

export default function ViewTravelMap() {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);
  const markerRef = useRef<naver.maps.Marker | null>(null);
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
  const { onSuccessGeolocation, onErrorGeolocation } = useGeolocation(
    mapRef,
    setCenter,
  );
  const [markerClickedId, setMarkerClickedId] = useState(-1);

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
  }, [mapRef.current]);

  const getPlaces = async () => {
    const placeList = await getNearPlaces(maxMinBounds, currentPage);
    if (!placeList.success) {
      alert('장소 데이터를 불러오는데 실패했습니다');
      return;
    }
    setAllPlaces(placeList.data.places);
    setTotalPages(placeList.data.total_pages);
  };
  useEffect(() => {
    if (!maxMinBounds.maxX) return;
    getPlaces();
  }, [maxMinBounds, currentPage]);

  return (
    <>
      <CustomControl mapRef={mapRef} center={center} />
      <NaverMap
        center={{
          lat: 37.5135869,
          lng: 127.0621708,
        }}
        zoom={15}
        ref={mapRef}
        onZoomChanged={() => console.log(mapRef.current?.getBounds())}
      >
        {allPlaces.map((place) => (
          <MapMarker
            key={place.id}
            {...place}
            markerClickedId={markerClickedId}
            setMarkerClickedId={setMarkerClickedId}
          />
        ))}
        {/* <MapPlaces allPlaces={allPlaces} totalPages={totalPages} /> */}
      </NaverMap>
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
