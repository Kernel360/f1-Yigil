'use client';
import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction, useContext, useState } from 'react';
import { AddSpotContext, TAddSpotAction } from '../spot/SpotContext';

interface TPostReviewProps {
  viewTitle?: boolean;
  dispatch: Dispatch<TAddSpotAction>;
}

export default function PostReview({ viewTitle, dispatch }: TPostReviewProps) {
  const { review } = useContext(AddSpotContext);

  const maxLength = 30;

  const onChangeReview = (e: EventFor<'input' | 'textarea', 'onChange'>) => {
    const { name, value } = e.currentTarget;
    if (name === 'review' && e.target.value.length > maxLength) {
      e.target.blur();
      e.target.focus();

      return;
    }

    dispatch({ type: 'SET_REVIEW', payload: { ...review, [name]: value } });
  };

  return (
    <form className="flex flex-col items-center gap-y-2 px-[22px]">
      {viewTitle && (
        <input
          className="w-full py-3 px-4 bg-gray-100 rounded-xl text-xl leading-0"
          type="text"
          value={review.title}
          name="title"
          required
          onChange={onChangeReview}
          placeholder="일정 제목을 입력하세요."
        />
      )}

      <div className="w-full">
        <textarea
          className="w-full p-4 bg-gray-100 resize-none rounded-xl"
          name="review"
          rows={5}
          maxLength={maxLength}
          required
          onChange={onChangeReview}
          value={review.review}
          placeholder="간단한 리뷰를 작성하세요.(최대 30자)"
        />
        <p className="flex justify-end text-gray-400 mr-4">{`${review.review.length} / 30`}</p>
      </div>
    </form>
  );
}
