'use client';
import { EventFor } from '@/types/type';
import React, { useState } from 'react';
import StarIcon from '/public/icons/star.svg';

/** setRating 함수를 props로 받아야 함 */
export default function PostRating() {
  const [ratingValue, setRating] = useState(1);
  const [hoverValue, setHoverValue] = useState(1);

  const onMouseEnter = (e: EventFor<'div', 'onKeyDown'>, value: number) => {
    e.key === 'Enter' && setRating(value);
  };

  const onClickStar = (value: number) => {
    setRating(value);
  };

  const onHoverStar = (value: number) => {
    setHoverValue(value);
  };

  return (
    <div className="flex justify-center items-center text-2xl gap-x-6">
      {[...Array(5)].map((_, i) => (
        <div key={i} tabIndex={0} onKeyDown={(e) => onMouseEnter(e, i + 1)}>
          <label htmlFor={`rate${i + 1}`}>
            <input
              className="hidden"
              type="radio"
              id={`rate${i + 1}`}
              value={i + 1}
              onClick={() => onClickStar(i + 1)}
            />
            <StarIcon
              className={`cursor-pointer w-12 h-12 ${
                i + 1 <= hoverValue
                  ? ' fill-[#FAbb15]'
                  : i + 1 <= ratingValue
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
  );
}
