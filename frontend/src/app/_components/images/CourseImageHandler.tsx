'use client';

import { useContext } from 'react';

import ImageHandler from '.';

import type { TImageData } from './ImageHandler';
import { CourseContext } from '@/context/travel/course/CourseContext';

export default function CourseImageHandler({ order }: { order: number }) {
  const [state, dispatch] = useContext(CourseContext);

  function setImages(nextImages: TImageData[]) {
    dispatch({
      type: 'SET_SPOT_IMAGES',
      payload: { data: nextImages, index: order },
    });
  }

  return (
    <ImageHandler images={state.spots[order].images} setImages={setImages} />
  );
}
