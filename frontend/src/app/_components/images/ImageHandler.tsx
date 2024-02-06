'use client';

import { useState } from 'react';

import ImageInput from './ImageInput';
import ImagesContainer from './ImagesContainer';

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

  const availableSpace = size - images.length;

  const blankSpaces = [...Array(availableSpace)];

  return (
    <section className="grid grid-rows-2 grid-cols-3 gap-2">
      <ImageInput availableSpace={availableSpace} addImages={addImages} />
      <ImagesContainer images={images} setImages={setImages} />
      {blankSpaces.map((_, i) => (
        <div
          key={i}
          className="aspect-square border-2 rounded-2xl border-gray-200 shrink-0"
        />
      ))}
    </section>
  );
}
