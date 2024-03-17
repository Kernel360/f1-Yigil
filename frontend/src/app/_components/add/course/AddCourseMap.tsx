'use client';

import { useContext, useEffect, useRef, useState } from 'react';
import { Container, Marker, NaverMap, useNavermaps } from 'react-naver-maps';
import AddTravelSearchResult from '../AddTravelSearchResult';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { isEqualPlace } from '@/context/travel/utils';

import ToastMsg from '../../ui/toast/ToastMsg';

import { getMap } from '../common/action';
import { basicMarker } from '../../naver-map/markers/basicMarker';
import { plusMarker } from '../../naver-map/markers/plusMarker';

const defaultCenter = {
  lat: 37.5135869,
  lng: 127.0621708,
};

export default function AddCourseMap() {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);
  const markerRef = useRef<naver.maps.Marker | null>(null);

  const [error, setError] = useState('');
  const [inform, setInform] = useState('');

  const [addTravelMapState, dispatchAddTravelMap] =
    useContext(AddTravelMapContext);

  const { current, selectedPlace } = addTravelMapState;

  useEffect(() => {
    if (mapRef.current) {
      mapRef.current.panTo(current.coords);
      mapRef.current.autoResize();
    }
  }, [current]);

  async function handleClick() {
    setError('');

    if (isEqualPlace(current, selectedPlace)) {
      dispatchAddTravelMap({ type: 'UNSELECT_PLACE' });
      setInform('');
      return;
    }

    dispatchAddTravelMap({
      type: 'SELECT_PLACE',
      payload: current,
    });

    setInform('⊕ 를 눌러 장소를 추가하세요.');
    setTimeout(() => setInform(''), 2000);
  }

  return (
    <section className="flex flex-col grow">
      <hr className="my-4" />
      <section className="relative grow">
        <div className="absolute w-full h-full">
          {/* <div className="bg-white">야호~</div> */}
          <Container
            style={{ position: 'absolute', width: '100%', height: '100%' }}
          >
            <NaverMap defaultCenter={defaultCenter} ref={mapRef}>
              {current.coords.lng !== 0 && current.coords.lat !== 0 && (
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
              )}
            </NaverMap>
          </Container>
        </div>
        {addTravelMapState.isSearchResultOpen && <AddTravelSearchResult />}
        {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
        {inform && <ToastMsg id={Date.now()} title={inform} timer={2000} />}
      </section>
    </section>
  );
}
