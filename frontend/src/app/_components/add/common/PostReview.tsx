'use client';

import { useState } from 'react';

import StarIcon from '/public/icons/star.svg';

import type { EventFor } from '@/types/type';
import type { TReview } from '@/context/travel/schema';

interface TPostReviewProps {
  viewTitle?: boolean;
  review: TReview;
  setReview: (nextReview: TReview) => void;
}

export default function PostReview({
  viewTitle,
  review,
  setReview,
}: TPostReviewProps) {
  const maxLength = 30;

  const [hoverValue, setHoverValue] = useState(1);

  const onSetStar = (value: number) => {
    const nextReview: TReview = {
      ...review,
      rate: value,
    };

    setReview(nextReview);
  };

  const onKeydown = (e: EventFor<'div', 'onKeyDown'>, value: number) => {
    if (e.key === 'Enter') {
      onSetStar(value);
      return;
    }
  };

  const onKeyUp = (e: EventFor<'div', 'onKeyUp'>, value: number) => {
    if (e.key === 'Tab') {
      onHoverStar(value);
      return;
    }
  };

  const onHoverStar = (value: number) => {
    setHoverValue(value);
  };

  const onChangeReview = (e: EventFor<'input' | 'textarea', 'onChange'>) => {
    const { name, value } = e.currentTarget;
    if (name === 'review' && e.target.value.length > maxLength) {
      e.target.blur();
      e.target.focus();

      return;
    }

    const nextReview: TReview = {
      ...review,
      content: value,
    };

    setReview(nextReview);
  };

  return (
    <section className="px-4 flex flex-col items-center gap-8">
      <div className="flex justify-center items-center text-2xl gap-x-6">
        {[...Array(5)].map((_, i) => (
          <div
            key={i}
            tabIndex={0}
            onKeyDown={(e) => onKeydown(e, i + 1)}
            onKeyUp={(e) => onKeyUp(e, i + 1)}
          >
            <label htmlFor={`rate${i + 1}`}>
              <input
                className="hidden"
                type="radio"
                id={`rate${i + 1}`}
                value={i + 1}
                onClick={() => onSetStar(i + 1)}
              />
              <StarIcon
                className={`cursor-pointer w-12 h-12 transition-all delay-80 ${
                  i + 1 <= hoverValue
                    ? ' fill-[#FAbb15]'
                    : i + 1 <= review.rate
                    ? 'fill-[#FACC15]'
                    : 'fill-gray-200'
                } `}
                onMouseEnter={() => onHoverStar(i + 1)}
                onMouseLeave={() => setHoverValue(0)}
              />
            </label>
          </div>
        ))}
      </div>
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
          value={review.content}
          placeholder="간단한 리뷰를 작성하세요.(최대 30자)"
        />
        <p className="flex justify-end text-gray-400 mr-4">{`${review.content.length} / 30`}</p>
      </div>
    </section>
  );
}
