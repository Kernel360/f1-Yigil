'use client';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';

import ImageHandler from '.';

import type { TImageData } from './ImageHandler';

export default function SpotImageHandler() {
  const [state, dispatch] = useContext(SpotContext);

  function setImages(nextImages: TImageData[]) {
    dispatch({ type: 'SET_IMAGES', payload: nextImages });
  }

  return <ImageHandler images={state.images} setImages={setImages} />;
}
