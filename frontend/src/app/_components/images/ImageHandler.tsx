'use client';

import { useContext } from 'react';

import ImageInput from './ImageInput';
import ImagesContainer from './ImagesContainer';
import { AddSpotContext } from '../add/spot/SpotContext';

import type { Dispatch } from 'react';

import type { TAddSpotAction } from '../add/spot/SpotContext';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { CourseContext } from '@/context/travel/course/CourseContext';

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
}) {
  const availableSpace = size - images.length;

  const blankSpaces = [...Array(availableSpace)];

  return (
    <section className="grid grid-rows-2 grid-cols-3 gap-2">
      <ImageInput
        availableSpace={availableSpace}
        images={images}
        setImages={setImages}
      />
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
