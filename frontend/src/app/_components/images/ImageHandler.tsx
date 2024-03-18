'use client';

import { useState } from 'react';
import ImageInput from './ImageInput';
import ImagesContainer from './ImagesContainer';
import ToastMsg from '../ui/toast/ToastMsg';

export interface TImageData {
  filename: string;
  uri: string;
}

export default function ImageHandler({
  images,
  setImages,
  size = 5,
}: {
  images: TImageData[];
  setImages: (newImages: TImageData[]) => void;
  size?: number;
  gridStyle?: string;
}) {
  const [error, setError] = useState('');

  const availableSpace = size - images.length;

  const blankSpaces = [...Array(availableSpace)];

  return (
    <section className="grid grid-rows-2 grid-cols-3 gap-2 my-4">
      <ImageInput
        availableSpace={availableSpace}
        images={images}
        setImages={setImages}
        invokeError={(title: string) => setError(title)}
      />
      <ImagesContainer images={images} setImages={setImages} />
      {blankSpaces.map((_, i) => (
        <div
          key={i}
          className="aspect-square border-2 rounded-2xl border-gray-200 shrink-0"
        />
      ))}
      {/* 에러 토스트 */}
      {error && (
        <div className="absolute">
          <ToastMsg
            id={Date.now()}
            key={Date.now()}
            title={error}
            timer={1000}
          />
        </div>
      )}
    </section>
  );
}
