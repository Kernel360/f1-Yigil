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
      <div className="px-6 py-10 flex justify-between items-center">
        <span className="pr-8 text-lg text-gray-400 shrink-0">이름</span>
        <span className="text-2xl font-medium">{state.place.name}</span>
      </div>
      <PostReview review={state.review} setReview={setReview} />
    </section>
  );
}
