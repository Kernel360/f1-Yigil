'use client';

import { useState } from 'react';
import ImageInput from './ImageInput';
import Image from 'next/image';

export interface TImageData {
  filename: string;
  uri: string;
}

export default function ImageHandler({ size }: { size: number }) {
  // 해당 상태는 상위로 끌어올려져야 함
  const [images, setImages] = useState<TImageData[]>([]);

  function addImages(newImages: TImageData[]) {
    if (images.length === 0) {
      setImages(newImages);

      return;
    }

    const currentImageNames = images.map((image) => image.filename);

    const deduplicated = [...newImages].filter(
      (image) => !currentImageNames.includes(image.filename),
    );

    const nextImages = [...images, ...deduplicated];

    setImages(nextImages);
  }

  function removeImage(filename: string) {
    const nextImages = images.filter((image) => image.filename !== filename);

    setImages(nextImages);
  }

  const availableSpace = size - images.length;

  return (
    <section className="grid grid-rows-2 grid-cols-3 gap-2">
      <ImageInput availableSpace={availableSpace} addImages={addImages} />
      {[...Array(size)].map((_, i) => (
        <button
          className="overflow-hidden aspect-square border-2 rounded-2xl border-gray-200 relative shrink-0"
          key={i}
          onClick={() => {
            if (images[i]) {
              removeImage(images[i].filename);
            }
          }}
        >
          {images[i] && <Image src={images[i].uri} alt={`image-${i}`} fill />}
          {images[i] && i === 0 && (
            <span className="absolute top-2 right-2 px-[8px] py-[2px] rounded-2xl border-2 border-main bg-white text-sm text-main">
              대표
            </span>
          )}
        </button>
      ))}
    </section>
  );
}
