'use client';

import { useContext } from 'react';

import ImageInput from './ImageInput';
import ImagesContainer from './ImagesContainer';
import { AddSpotContext } from '../add/spot/SpotContext';

import type { Dispatch } from 'react';

import type { TAddSpotAction } from '../add/spot/SpotContext';

export interface TImageData {
  filename: string;
  uri: string;
}

export default function ImageHandler({
  dispatch,
  size,
}: {
  dispatch: Dispatch<TAddSpotAction>;
  size: number;
}) {
  const addSpotState = useContext(AddSpotContext);
  const images = addSpotState.images;

  function addImages(newImages: TImageData[]) {
    if (newImages.length === 0) {
      return;
    }

    const currentImageNames = images.map((image) => image.filename);

    const deduplicated = newImages.filter(
      (image) => !currentImageNames.includes(image.filename),
    );

    const nextImages = [...images, ...deduplicated];

    dispatch({ type: 'SET_IMAGES', payload: nextImages });
  }

  const availableSpace = size - images.length;

  const blankSpaces = [...Array(availableSpace)];

  return (
    <section className="grid grid-rows-2 grid-cols-3 gap-2">
      <ImageInput availableSpace={availableSpace} addImages={addImages} />
      <ImagesContainer dispatch={dispatch} />
      {blankSpaces.map((_, i) => (
        <div
          key={i}
          className="aspect-square border-2 rounded-2xl border-gray-200 shrink-0"
        />
      ))}
    </section>
  );
}
