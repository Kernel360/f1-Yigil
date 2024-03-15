'use client';

import Image from 'next/image';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { AddTravelMapContext } from '../AddTravelMapProvider';
import { isDefaultPlace } from '@/context/travel/place/reducer';

import { InfoTitle } from '../common';
import AddTravelSearchBar from '../AddTravelSearchBar';

import PlaceholderImage from '/public/images/placeholder.png';
import AddSpotMap from './AddSpotMap';

const tempImage =
  'http://cdn.yigil.co.kr/images/a188a3e6-6a33-4013-b97d-103d570f958a_%EC%86%94%ED%96%A5%EA%B8%B0%20%EC%A7%80%EB%8F%84%20%EC%9D%B4%EB%AF%B8%EC%A7%80.png';

export default function AddSpotPlace() {
  const [isOpen] = useContext(AddTravelMapContext);
  const [spot] = useContext(SpotContext);

  const { place } = spot;

  return (
    <section className={`flex flex-col grow`}>
      <div
        className={`h-80 flex flex-col justify-center duration-500 ease-in-out ${
          isOpen
            ? 'opacity-0 max-h-0 transition-all'
            : 'opacity-100 max-h-full transition-all'
        }`}
      >
        <InfoTitle label="장소" additionalLabel="를 입력하세요." />
      </div>
      {isDefaultPlace({ type: 'spot', data: place }) && <AddTravelSearchBar />}
      {isOpen ? (
        <AddSpotMap />
      ) : (
        !isDefaultPlace({ type: 'spot', data: place }) && (
          <section className="p-4 flex flex-col gap-8">
            <div className="px-4 flex justify-between items-center">
              <span className="pr-8 text-lg text-gray-400 shrink-0">이름</span>
              <span className="text-2xl font-medium">{place.name}</span>
            </div>
            <div className="px-4 flex justify-between items-center">
              <span className="pr-8 text-lg text-gray-400 shrink-0">주소</span>
              <span className="text-xl font-medium">{place.address}</span>
            </div>
            <div className="my-4 relative aspect-video">
              <Image
                className="rounded-xl object-cover"
                priority
                // src={place.mapImageUrl}
                src={tempImage}
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
