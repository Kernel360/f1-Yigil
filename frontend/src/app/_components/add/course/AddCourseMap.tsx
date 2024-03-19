'use client';

import { useContext, useEffect, useMemo, useRef, useState } from 'react';
import {
  Container,
  Marker,
  NaverMap,
  Overlay,
  useNavermaps,
} from 'react-naver-maps';
import AddTravelSearchResult from '../AddTravelSearchResult';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { CourseContext } from '@/context/travel/course/CourseContext';
import { isEqualPlace } from '@/context/travel/utils';

import ToastMsg from '../../ui/toast/ToastMsg';

import { getMap } from '../common/action';
import { basicMarker } from '../../naver-map/markers/basicMarker';
import { plusMarker } from '../../naver-map/markers/plusMarker';
import ChipCarousel from '../../ui/carousel/ChipCarousel';
import { SPOTS_COUNT } from '@/context/travel/schema';

const defaultCenter = {
  lat: 37.5135869,
  lng: 127.0621708,
};

export default function AddCourseMap() {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);
  const markerRef = useRef<naver.maps.Marker>(null);
  const courseMarkersRef = useRef<naver.maps.Marker[]>([]);

  const [error, setError] = useState('');
  const [inform, setInform] = useState('');

  const [addTravelMapState, dispatchAddTravelMap] =
    useContext(AddTravelMapContext);
  const [course, dispatchCourse] = useContext(CourseContext);

  const { current, selectedPlace } = addTravelMapState;

  function onChangedMap() {
    dispatchAddTravelMap({ type: 'UNSELECT_PLACE' });
  }

  useEffect(() => {
    mapRef.current?.panTo(current.coords);
    mapRef.current?.autoResize();
  }, [current]);

  useEffect(() => {
    if (!mapRef.current) {
      return;
    }

    courseMarkersRef.current.forEach((marker) => marker.setMap(null));

    const courseMarkers = course.spots.map(
      ({ place }) =>
        new naver.maps.Marker({ position: place.coords, title: place.name }),
    );

    courseMarkersRef.current = courseMarkers;

    courseMarkersRef.current.forEach((marker) => marker.setMap(mapRef.current));
  }, [course.spots]);

  const places = course.spots.map((spot) => spot.place);

  async function handleClick() {
    try {
      if (course.spots.length === SPOTS_COUNT) {
        dispatchAddTravelMap({ type: 'UNSELECT_PLACE' });
        console.error('더 이상 추가할 수 없습니다!');
        throw new Error('더 이상 추가할 수 없습니다!');
      }

      if (places.some((place) => isEqualPlace(place, selectedPlace))) {
        dispatchAddTravelMap({ type: 'UNSELECT_PLACE' });
        console.error('같은 장소를 추가할 수 없습니다!');
        throw new Error('같은 장소를 추가할 수 없습니다!');
      }

      const { name, address, coords } = current;
      const mapImageUrl = await getMap(name, address, coords);

      if (mapImageUrl.status === 'failed') {
        console.error(mapImageUrl.error);
        throw new Error(mapImageUrl.error);
      }

      if (!isEqualPlace(current, selectedPlace)) {
        dispatchAddTravelMap({
          type: 'SELECT_PLACE',
          payload: current,
        });
        setInform('⊕ 를 눌러 장소를 추가하세요.');
        return;
      }

      dispatchCourse({
        type: 'ADD_SPOT',
        payload: {
          ...selectedPlace,
          mapImageUrl: mapImageUrl.data,
        },
      });

      dispatchAddTravelMap({ type: 'RESET_CURRENT_PLACE' });
      dispatchAddTravelMap({ type: 'UNSELECT_PLACE' });
    } catch (error) {
      console.error(error);
      if (error instanceof Error) {
        setError(error.message);
      }
    } finally {
      setTimeout(() => setInform(''), 2000);
      setTimeout(() => setError(''), 2000);
    }
  }

  function handleCancel(index: number) {
    const item = course.spots[index];

    dispatchCourse({ type: 'REMOVE_SPOT', payload: item });
  }

  return (
    <section className="flex flex-col grow">
      <hr className="my-4" />
      <section className="relative grow">
        <div className="absolute w-full h-full">
          <Container
            style={{ position: 'absolute', width: '100%', height: '100%' }}
          >
            <NaverMap
              defaultCenter={defaultCenter}
              ref={mapRef}
              onBoundsChanged={onChangedMap}
              onSizeChanged={onChangedMap}
            >
              <Marker
                ref={markerRef}
                title={current.name}
                position={current.coords}
                icon={
                  isEqualPlace(current, selectedPlace)
                    ? plusMarker({ name: current.name })
                    : basicMarker(current.name)
                }
                onClick={handleClick}
              ></Marker>
              {/* {courseMarkerRef.current.map((spotMarker, index) => (
                <Overlay key={index} element={spotMarker} />
              ))} */}
            </NaverMap>
          </Container>
        </div>
        <section className="absolute w-full">
          <ChipCarousel onCancel={handleCancel} spots={course.spots} />
        </section>
        {addTravelMapState.isSearchResultOpen && <AddTravelSearchResult />}
        {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
        {inform && <ToastMsg id={Date.now()} title={inform} timer={2000} />}
      </section>
    </section>
  );
}
