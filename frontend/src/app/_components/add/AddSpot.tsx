'use client';

import { useReducer, useState } from 'react';

import { makeInitialStep, reducer, StepNavigation } from './common/step';
import ProgressIndicator from './common/ProgressIndicator';
import { switchAddSpot } from './spot';

import type { DataInput, Making } from './common/step/types';

export default function AddSpot() {
  const [step, dispatch] = useReducer(reducer, makeInitialStep(true, false));
  const { makingStep, inputStep } = step;

  const makingSpotStep = makingStep as Making.TSpot;
  const dataFromNewStep = inputStep as DataInput.TDataFromNew;

  const placeName = '이구성수';
  const placeAddress = '서울 성동구 모시깽이';

  const [reviewState, setReviewState] = useState({ review: '' });

  const AddComponent = switchAddSpot(
    placeName,
    placeAddress,
    makingSpotStep,
    dataFromNewStep,
    reviewState,
    setReviewState,
  );

  return (
    <section>
      <StepNavigation
        currentStep={step}
        next={() => dispatch({ type: 'next' })}
        previous={() => dispatch({ type: 'previous' })}
      />
      <ProgressIndicator step={step} />
      {AddComponent}
    </section>
  );
}
