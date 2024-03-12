'use client';

import { useContext } from 'react';

import { StepContext } from '@/context/travel/step/StepContext';

export default function Navigation() {
  const [step, dispatch] = useContext(StepContext);

  return (
    <nav className="flex justify-between">
      <button
        onClick={() => {
          dispatch({ type: 'PREVIOUS' });
        }}
      >
        이전
      </button>
      <span>{step.data.label}</span>
      <button onClick={() => dispatch({ type: 'NEXT' })}>다음</button>
    </nav>
  );
}
