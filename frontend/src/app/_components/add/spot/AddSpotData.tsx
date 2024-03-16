'use client';

import { useContext } from 'react';
import { StepContext } from '@/context/travel/step/StepContext';

import AddSpotPlace from './AddSpotPlace';
import AddSpotImages from './AddSpotImages';
import AddSpotReview from './AddSpotReview';
import AddSpotConfirm from './AddSpotConfirm';

import AddSpotPlace from './AddSpotPlace';

export default function AddSpotData() {
  const [step] = useContext(StepContext);

  const { value } = step.data;

  switch (value) {
    // Spot은 0번이 없음
    case 0:
      return <></>;
    case 1:
      return <AddSpotPlace />;
    case 2:
      return <AddSpotImages />;
    case 3:
      return <AddSpotReview />;
    case 4:
      return <AddSpotConfirm />;
  }
}
