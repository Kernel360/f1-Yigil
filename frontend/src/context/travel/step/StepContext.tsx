'use client';

import { createContext, useReducer } from 'react';

import reducer, { createInitialStep, defaultStepState } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TStepState, TStepAction } from './reducer';
import type { TChoosePlace } from '../schema';

export const StepContext = createContext<[TStepState, Dispatch<TStepAction>]>([
  defaultStepState,
  () => {},
]);

export default function StepProvider({
  place,
  course,
  children,
}: {
  place?: TChoosePlace;
  course?: boolean;
  children: ReactNode;
}) {
  const initialState = createInitialStep(place, course);

  const [state, dispatch] = useReducer(reducer, initialState);

  return (
    <StepContext.Provider value={[state, dispatch]}>
      {children}
    </StepContext.Provider>
  );
}
