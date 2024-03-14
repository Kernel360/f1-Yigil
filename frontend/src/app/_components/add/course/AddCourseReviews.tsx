'use client';

import { useContext, useState } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';
import { InfoTitle, PostReview } from '../common';
import SelectSpot from './SelectSpot';

import type { TReview } from '@/context/travel/schema';

export default function AddCourseReview() {
  const [state, dispatch] = useContext(CourseContext);
  const [index, setIndex] = useState(0);

  function selectIndex(nextIndex: number) {
    setIndex(nextIndex);
  }

  function setReview(nextReview: TReview) {
    dispatch({ type: 'SET_SPOT_REVIEW', payload: { data: nextReview, index } });
  }

  function setCourseReview(nextReview: TReview) {
    dispatch({ type: 'SET_COURSE_REVIEW', payload: nextReview });
  }

  return (
    <section className="flex flex-col justify-center grow">
      <InfoTitle label="리뷰" additionalLabel="를 남기세요." />
      <SelectSpot
        key="reviews"
        index={index}
        selectIndex={selectIndex}
        review
      />
      {index !== -1 && (
        <div className="p-4 flex justify-between items-center">
          <span className="pr-8 text-lg text-gray-400 shrink-0">이름</span>
          <span className="text-2xl font-medium">
            {state.spots[index].place.name}
          </span>
        </div>
      )}
      {index === -1 ? (
        <PostReview
          review={state.review}
          setReview={setCourseReview}
          viewTitle
        />
      ) : (
        <PostReview review={state.spots[index].review} setReview={setReview} />
      )}
    </section>
  );
}
