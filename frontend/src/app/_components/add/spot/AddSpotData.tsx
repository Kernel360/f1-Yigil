'use client';

import { useContext } from 'react';
import { SpotStepContext } from '@/context/travel/step/spot/SpotStepContext';

import AddSpotPlace from './AddSpotPlace';
import AddSpotImages from './AddSpotImages';
import AddSpotReview from './AddSpotReview';
import AddSpotConfirm from './AddSpotConfirm';

export default function AddSpotData() {
  const [step] = useContext(SpotStepContext);

  const { value } = step;

  switch (value) {
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
