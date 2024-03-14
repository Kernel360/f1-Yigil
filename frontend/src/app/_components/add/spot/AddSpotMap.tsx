'use client';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { AddTravelMapContext } from '../AddTravelMapProvider';

import { InfoTitle } from '../common';
import AddTravelSearchBar from '../AddTravelSearchBar';

export default function AddSpotMap() {
  const [isOpen, setIsOpen] = useContext(AddTravelMapContext);
  const [] = useContext(SpotContext);

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
      <AddTravelSearchBar />
    </section>
  );
}
