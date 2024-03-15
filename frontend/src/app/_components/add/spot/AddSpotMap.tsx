'use client';

import { useContext, useEffect, useRef, useState } from 'react';
import { Container, Marker, NaverMap, useNavermaps } from 'react-naver-maps';
import AddTravelSearchResult from '../AddTravelSearchResult';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { PlaceContext } from '@/context/travel/place/PlaceContext';

import { getMap } from '../common/action';
import { basicMarker } from '../../naver-map/markers/basicMarker';

const defaultCenter = {
  lat: 37.5135869,
  lng: 127.0621708,
};

export default function AddSpotMap() {
  const navermaps = useNavermaps();
  const mapRef = useRef<naver.maps.Map>(null);
  const markerRef = useRef<naver.maps.Marker | null>(null);

  const [addTravelMapState] = useContext(AddTravelMapContext);
  const [, dispatchPlace] = useContext(PlaceContext);

  const { current } = addTravelMapState;

  useEffect(() => {
    if (current.type === 'spot' && mapRef.current) {
      mapRef.current.autoResize();
      mapRef.current.panTo(current.place.coords);
    }
  }, [current]);

  if (current.type === 'course') {
    return null;
  }

  const currentPlace = current.place;

  async function handleClick() {
    const { name, roadAddress, coords } = currentPlace;

    try {
      const imageUrl = await getMap(name, roadAddress, coords);

      if (imageUrl.status === 'failed') {
        throw new Error('지도 이미지를 얻어올 수 없습니다.');
      }

      dispatchPlace({
        type: 'SET_PLACE',
        payload: {
          name,
          address: roadAddress,
          coords,
          mapImageUrl: imageUrl.data,
        },
      });
    } catch (error) {
      // Toast
      console.error(error);
    }
  }

  const { lat, lng } = currentPlace.coords;

  return (
    <section className="flex flex-col grow">
      <hr className="my-4" />
      <section className="relative grow">
        <div className="absolute w-full h-full">
          <Container
            style={{ position: 'absolute', width: '100%', height: '100%' }}
          >
            <NaverMap defaultCenter={defaultCenter} ref={mapRef}>
              {lng !== 0 && lat !== 0 && (
                <Marker
                  ref={markerRef}
                  title={currentPlace.name}
                  position={currentPlace.coords}
                  icon={basicMarker(currentPlace.name)}
                  onClick={handleClick}
                ></Marker>
              )}
            </NaverMap>
          </Container>
        </div>
        {addTravelMapState.isSearchResultOpen && <AddTravelSearchResult />}
      </section>
    </section>
  );
}
