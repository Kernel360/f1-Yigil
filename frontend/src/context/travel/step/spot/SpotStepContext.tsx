'use client';

import { createContext, useReducer } from 'react';

import reducer, { initialSpotStepState } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TSpotStepState, TSpotStepAction } from './reducer';

export const SpotStepContext = createContext<
  [TSpotStepState, Dispatch<TSpotStepAction>]
>([initialSpotStepState, () => {}]);

export default function SpotStepProvider({
  children,
}: {
  children: ReactNode;
}) {
  const [state, dispatch] = useReducer(reducer, initialSpotStepState);

  return (
    <SpotStepContext.Provider value={[state, dispatch]}>
      {children}
    </SpotStepContext.Provider>
  );
}
