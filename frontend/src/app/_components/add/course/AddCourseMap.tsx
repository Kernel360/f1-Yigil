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
  const courseMarkersRef = useRef<Map<string, naver.maps.Marker>>(new Map());

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

  const courseSpotPlaceData = useMemo(() => {
    return course.spots
      .map((spot) => spot.place)
      .map(({ name, coords: { lat, lng } }) => {
        return { title: name, lat, lng };
      });
  }, [course.spots]);

  useEffect(() => {
    if (!mapRef.current) {
      return;
    }

    if (course.spots.length > courseMarkersRef.current.size) {
      const {
        place: { name, coords },
      } = course.spots[course.spots.length - 1];

      const newMarker = new naver.maps.Marker({
        title: name,
        position: coords,
      });

      newMarker.setMap(mapRef.current);
      courseMarkersRef.current.set(newMarker.get('_nmarker_id'), newMarker);

      return;
    }

    if (course.spots.length < courseMarkersRef.current.size) {
      const prevMarkers = Array.from(courseMarkersRef.current.entries());

      if (course.spots.length === 0) {
        const [, value] = prevMarkers[0];
        value.setMap(null);
        courseMarkersRef.current.clear();
        return;
      }

      for (let i = 0; i < prevMarkers.length - 1; i++) {
        const place = courseSpotPlaceData[i];

        const [key, value] = prevMarkers[i];
        const markerTitle = value.getTitle();
        const { x, y } = value.getPosition();

        if (place.title === markerTitle) {
          continue;
        }

        if (place.lng === x && place.lat === y) {
          continue;
        }

        value.setMap(null);
        courseMarkersRef.current.delete(key);

        return;
      }

      // 반복문 통과 시 마지막이 제거된 마커
      const [key, value] = prevMarkers[prevMarkers.length - 1];
      value.setMap(null);
      courseMarkersRef.current.delete(key);
    }
  }, [course.spots, courseSpotPlaceData]);

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
