'use client';

import { useContext } from 'react';

import { CourseContext } from '@/context/travel/course/CourseContext';
import { SPOTS_COUNT } from '@/context/travel/schema';

export default function SelectSpot({
  index,
  disabled,
  selectIndex,
  review,
}: {
  index: number;
  disabled?: boolean;
  selectIndex: (nextIndex: number) => void;
  review?: boolean;
}) {
  const [state] = useContext(CourseContext);

  const indices = [...Array(SPOTS_COUNT)].map((_, i) => i);

  const isButtonDisabled = (value: number) => {
    return disabled || value >= state.spots.length;
  };

  return (
    <nav className="p-4 flex gap-4 justify-center">
      {indices.map((value) => (
        <button
          className={`w-12 h-12 text-xl rounded-full ${
            isButtonDisabled(value) && 'text-gray-400'
          } ${value === index && 'bg-blue-500 text-white'}`}
          key={value}
          disabled={isButtonDisabled(value)}
          onClick={() => selectIndex(value)}
        >
          {value + 1}
        </button>
      ))}
      {review && (
        <button
          className={`w-12 h-12 text-xl rounded-full ${
            index === -1 && 'bg-blue-500 text-white'
          }`}
          onClick={() => selectIndex(-1)}
        >
          코스
        </button>
      )}
    </nav>
  );
}
