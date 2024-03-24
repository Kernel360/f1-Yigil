'use client';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';

import ImageHandler from '.';

import type { TImageData } from './ImageHandler';

export default function SpotImageHandler() {
  const [state, dispatch] = useContext(SpotContext);

  if (state.images.type === 'exist') {
    return null;
  }

  function setImages(nextImages: { type: 'new'; data: TImageData[] }) {
    dispatch({
      type: 'SET_IMAGES',
      payload: nextImages,
    });
  }

  return <ImageHandler images={state.images.data} setImages={setImages} />;
}
