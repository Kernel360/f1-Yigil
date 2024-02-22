'use client';

import Image from 'next/image';

import { useContext } from 'react';
import { AddSpotContext } from '../spot/SpotContext';

export default function AddPlaceInfo() {
  const { name, address, spotMapImageUrl } = useContext(AddSpotContext);

  return (
    <section className="p-4 flex flex-col gap-8 grow">
      <div className="px-4 flex justify-between items-center">
        <span className="text-gray-500">이름</span>
        <span className="font-medium text-2xl">{name}</span>
      </div>
      <div className="px-4 flex justify-between items-center">
        <span className="text-gray-500">주소</span>
        <span className="font-medium">{address}</span>
      </div>
      <div className="w-full h-80 p-4 relative">
        <Image
          src={spotMapImageUrl}
          alt="Example static map"
          fill
          unoptimized
        />
      </div>
    </section>
  );
}
