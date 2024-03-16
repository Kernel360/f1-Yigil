'use client';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';

import { InfoTitle } from '../common';
import SpotImageHandler from '../../images/SpotImageHandler';

export default function AddSpotImages() {
  const [spot] = useContext(SpotContext);

  return (
    <section className="flex flex-col justify-center grow">
      <InfoTitle label="사진" additionalLabel="을 업로드하세요." />
      <div className="px-6 py-10 flex justify-between items-center">
        <span className="pr-8 text-lg text-gray-400 shrink-0">이름</span>
        <span className="text-2xl font-medium">{spot.place.name}</span>
      </div>
      <div className="px-4">
        <SpotImageHandler />
      </div>
    </section>
  );
}
