'use client';

import { useContext } from 'react';
import { SpotStepContext } from '@/context/travel/step/spot/SpotStepContext';

import Progress from '../Progress';

export default function SpotProgress() {
  const [step] = useContext(SpotStepContext);

  return <Progress value={step.value} />;
}
