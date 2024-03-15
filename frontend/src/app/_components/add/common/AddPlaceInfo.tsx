'use client';

import Image from 'next/image';

import { useContext } from 'react';
import { AddSpotContext } from '../spot/SpotContext';

export default function AddPlaceInfo() {
  const { name, address, spotMapImageUrl } = useContext(AddSpotContext);

  return (
    <section className="p-4 flex flex-col gap-8 grow">
      <div className="px-4 flex justify-between items-center">
        <span className="text-gray-500 shrink-0 pr-8">이름</span>
        <span className="font-medium text-2xl">{name}</span>
      </div>
      <div className="px-4 flex justify-between items-center">
        <span className="text-gray-500">주소</span>
        <span className="font-medium">{address}</span>
      </div>
      <div className="h-3/5 p-4 relative aspect-video">
        <Image src={spotMapImageUrl} alt={`${name} 지도 이미지`} fill />
      </div>
    </section>
  );
}
