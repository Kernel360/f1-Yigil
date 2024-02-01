'use client';

import { useState } from 'react';
import ImageInput from './ImageInput';
import Image from 'next/image';

export interface TImageData {
  index: number;
  filename: string;
  uri: string;
}

export default function ImageHandler({ size }: { size: number }) {
  // 해당 상태는 상위로 끌어올려져야 함
  const [images, setImages] = useState<TImageData[]>([]);

  const availableSpace = size - images.length;

  return (
    <section className="grid grid-rows-2 grid-cols-3 gap-2">
      <ImageInput availableSpace={availableSpace} />
      {[...Array(size)].map((_, i) => (
        <div
          key={i}
          className="overflow-hidden aspect-square border-2 rounded-2xl border-gray-200 relative shrink-0"
        >
          {images[i] && (
            <Image src={images[i].uri} alt={`image-${images[i].index}`} fill />
          )}
          {images[i] && i === 0 && (
            <span className="absolute top-1 right-1 px-[8px] py-[2px] rounded-2xl border-2 border-main bg-white text-sm text-main">
              대표
            </span>
          )}
        </div>
      ))}
    </section>
  );
}
