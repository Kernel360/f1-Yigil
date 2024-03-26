'use client';

import { useContext } from 'react';

import ImageHandler from '.';

import type { TImageData } from './ImageHandler';
import { CourseContext } from '@/context/travel/course/CourseContext';

export default function CourseImageHandler({ order }: { order: number }) {
  const [state, dispatch] = useContext(CourseContext);

  const images = state.spots[order].images;

  if (images.type === 'exist') {
    return null;
  }

  function setImages(nextImages: { type: 'new'; data: TImageData[] }) {
    dispatch({
      type: 'SET_SPOT_IMAGES',
      payload: { data: nextImages, index: order },
    });
  }

  return <ImageHandler images={images.data} setImages={setImages} />;
}
