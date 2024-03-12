'use client';

import { useContext } from 'react';

import { StepContext } from '@/context/travel/step/StepContext';
import { SpotContext } from '@/context/travel/spot/SpotContext';

export default function AddSpotContent() {
  const [step, dispatchStep] = useContext(StepContext);
  const [spot, dispatchSpot] = useContext(SpotContext);

  return (
    <section>
      {step.data.value === 1 ? <>{step.data.label}</> : <>{step.data.label}</>}
    </section>
  );
}
