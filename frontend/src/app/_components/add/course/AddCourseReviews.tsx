'use client';

import { useContext } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';
import { InfoTitle, PostReview } from '../common';

import type { TReview } from '@/context/travel/schema';

export default function AddCourseReview({
  index,
  selectIndex,
}: {
  index: number;
  selectIndex: (nextIndex: number) => void;
}) {
  const [state, dispatch] = useContext(CourseContext);

  function setReview(nextReview: TReview) {
    if (nextReview.title) {
      dispatch({ type: 'SET_COURSE_REVIEW', payload: nextReview });
    }

    dispatch({ type: 'SET_SPOT_REVIEW', payload: { data: nextReview, index } });
  }

  return (
    <section className="flex flex-col justify-center grow">
      <InfoTitle label="리뷰" additionalLabel="를 남기세요." />
      <div className="flex flex-col">
        <button onClick={() => selectIndex(index - 1)}>Previous</button>
        <button onClick={() => selectIndex(index + 1)}>Next</button>
        <span>{`${index + 1} / ${state.spots.length}`}</span>
      </div>
      <div className="px-4 pb-4 flex justify-between items-center">
        <span className="text-gray-400">이름</span>
        <span className="text-xl">{state.spots[index].place.name}</span>
      </div>
      <PostReview review={state.spots[index].review} setReview={setReview} />
    </section>
  );
}
