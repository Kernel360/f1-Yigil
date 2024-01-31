'use client';
import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction } from 'react';

interface TPostReviewProps {
  viewTitle?: boolean;
  reviewState: {
    title?: string;
    review: string;
  };
  setReviewState: Dispatch<SetStateAction<{ title?: string; review: string }>>;
}

export default function PostReview({
  viewTitle,
  reviewState,
  setReviewState,
}: TPostReviewProps) {
  const onChangeReview = (e: EventFor<'input' | 'textarea', 'onChange'>) => {
    const { name, value } = e.currentTarget;
    setReviewState({ ...reviewState, [name]: value });
  };

  return (
    <form className="flex flex-col items-center gap-y-2 px-[22px]">
      {viewTitle && (
        <input
          className="w-full py-3 px-4 bg-gray-100 rounded-xl text-xl leading-0"
          type="text"
          value={reviewState.title}
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
          maxLength={29}
          required
          onChange={onChangeReview}
          placeholder="간단한 리뷰를 작성하세요.(최대 30자)"
        />
        <p className="flex justify-end text-gray-400 mr-4">{`${reviewState.review.length} / 30`}</p>
      </div>
    </form>
  );
}
