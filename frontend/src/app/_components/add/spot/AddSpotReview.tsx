'use client';

import { useContext } from 'react';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { InfoTitle, PostReview } from '../common';

import type { TReview } from '@/context/travel/schema';

export default function AddSpotReview() {
  const [state, dispatch] = useContext(SpotContext);

  function setReview(nextReview: TReview) {
    dispatch({ type: 'SET_REVIEW', payload: nextReview });
  }

  return (
    <section className="flex flex-col justify-center grow">
      <InfoTitle label="리뷰" additionalLabel="를 남기세요." />
      <div className="px-4 pb-4 flex justify-between items-center">
        <span className="text-gray-400">이름</span>
        <span className="text-xl">{state.place.name}</span>
      </div>
      <PostReview review={state.review} setReview={setReview} />
    </section>
  );
}
