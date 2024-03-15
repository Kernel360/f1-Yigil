'use client';

import { StepContext } from '@/context/travel/step/StepContext';
import { useContext } from 'react';

import AddSpotPlace from './AddSpotPlace';

export default function AddSpotData() {
  const [step] = useContext(StepContext);

  const { label, value } = step.data;

  switch (value) {
    // Spot은 0번이 없음
    case 0:
      return <></>;
    case 1:
      return <AddSpotPlace />;
    case 2:
      return <>{label}</>;
    case 3:
      return <>{label}</>;
    case 4:
      return <>{label}</>;
  }
}
