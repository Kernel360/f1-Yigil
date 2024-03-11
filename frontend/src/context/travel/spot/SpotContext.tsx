'use client';

import { createContext, useReducer } from 'react';
import { initialSpotState, reducer } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TSpotState } from '../schema';
import type { TSpotAction } from './reducer';

export const SpotContext = createContext<[TSpotState, Dispatch<TSpotAction>]>([
  initialSpotState,
  () => {},
]);

export default function SpotProvider({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(reducer, initialSpotState);

  return (
    <SpotContext.Provider value={[state, dispatch]}>
      {children}
    </SpotContext.Provider>
  );
}
