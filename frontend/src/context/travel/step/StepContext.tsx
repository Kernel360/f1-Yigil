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
  course,
  children,
}: {
  course?: boolean;
  children: ReactNode;
}) {
  const initialState = createInitialStep(course);

  const [state, dispatch] = useReducer(reducer, initialState);

  return (
    <StepContext.Provider value={[state, dispatch]}>
      {children}
    </StepContext.Provider>
  );
}
