'use client';

import { useContext } from 'react';

import { StepContext } from '@/context/travel/step/StepContext';

export default function Navigation() {
  const [step, dispatch] = useContext(StepContext);

  return (
    <nav className="mx-4 flex justify-between items-center">
      <button
        onClick={() => {
          dispatch({ type: 'PREVIOUS' });
        }}
      >
        이전
      </button>
      <span className="text-xl text-semibold text-gray-900">
        {step.data.label}
      </span>
      {step.data.value === 4 ? (
        <button className="text-blue-500">확정</button>
      ) : (
        <button onClick={() => dispatch({ type: 'NEXT' })}>다음</button>
      )}
    </nav>
  );
}
