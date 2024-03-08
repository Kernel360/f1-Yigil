'use client';

import { useContext, useState } from 'react';
import { AddSpotContext } from '../spot/SpotContext';

import StarIcon from '/public/icons/star.svg';

import type { Dispatch } from 'react';
import type { EventFor } from '@/types/type';
import type { TAddSpotAction } from '../spot/SpotContext';

export default function PostRating({
  dispatch,
}: {
  dispatch: Dispatch<TAddSpotAction>;
}) {
  const addSpotState = useContext(AddSpotContext);

  const [hoverValue, setHoverValue] = useState(1);

  const onMouseEnter = (e: EventFor<'div', 'onKeyDown'>, value: number) => {
    e.key === 'Enter' && dispatch({ type: 'SET_RATING', payload: value });
  };

  const onClickStar = (value: number) => {
    dispatch({ type: 'SET_RATING', payload: value });
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
              className={`cursor-pointer w-12 h-12 transition-all delay-80 ${
                i + 1 <= hoverValue
                  ? ' fill-[#FAbb15]'
                  : i + 1 <= addSpotState.rating
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
