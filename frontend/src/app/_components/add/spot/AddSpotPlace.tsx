'use client';

import Image from 'next/image';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

import { isDefaultPlace } from '@/context/travel/place/reducer';

import { InfoTitle } from '../common';
import AddTravelSearchBar from '../AddTravelSearchBar';
import AddSpotMap from './AddSpotMap';

import PlaceholderImage from '/public/images/placeholder.png';

export default function AddSpotPlace() {
  const [travelMapState] = useContext(AddTravelMapContext);
  const [spot] = useContext(SpotContext);

  const { isMapOpen } = travelMapState;
  const { place } = spot;

  return (
    <section className={`flex flex-col grow`}>
      <div
        className={`h-80 flex flex-col justify-center duration-500 ease-in-out ${
          isMapOpen
            ? 'collapse opacity-0 max-h-0 transition-all'
            : 'visible opacity-100 max-h-full transition-all'
        }`}
      >
        <InfoTitle label="장소" additionalLabel="를 입력하세요." />
      </div>
      {isDefaultPlace({ type: 'spot', data: place }) && <AddTravelSearchBar />}
      {isMapOpen ? (
        <>
          <AddSpotMap />
        </>
      ) : (
        !isDefaultPlace({ type: 'spot', data: place }) && (
          <section className="p-4 flex flex-col gap-8">
            <div className="px-4 flex justify-between items-center">
              <span className="pr-8 text-lg text-gray-400 shrink-0">이름</span>
              <span className="text-2xl font-medium text-end">
                {place.name}
              </span>
            </div>
            <div className="px-4 flex justify-between items-center">
              <span className="pr-8 text-lg text-gray-400 shrink-0">주소</span>
              <span className="text-xl font-medium text-end">
                {place.address}
              </span>
            </div>
            <div className="my-4 relative aspect-video">
              <Image
                className="rounded-xl object-cover"
                priority
                src={place.mapImageUrl}
                placeholder="blur"
                blurDataURL={PlaceholderImage.blurDataURL}
                alt={`${place.name} 지도 이미지`}
                fill
              />
            </div>
          </section>
        )
      )}
    </section>
  );
}
