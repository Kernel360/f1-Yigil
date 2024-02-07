'use client';

import { useReducer } from 'react';

import { makeInitialStep, reducer, StepNavigation } from './common/step';
import ProgressIndicator from './common/ProgressIndicator';

export default function AddSpot() {
  const [step, dispatch] = useReducer(reducer, makeInitialStep(true, false));

  let AddComponent;

  switch (step.makingStep.data.label) {
    case '장소 입력': {
      AddComponent = <></>;
    }
    case '정보 입력': {
      switch (step.inputStep.data.label) {
        case '주소': {
          // AddComponent
        }
      }
    }
  }

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
