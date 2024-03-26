'use client';

import { createContext, useReducer } from 'react';
import reducer, { createInitialSpotState, initialSpotState } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TChoosePlace, TSpotState } from '../schema';
import type { TSpotAction } from './reducer';

export const SpotContext = createContext<[TSpotState, Dispatch<TSpotAction>]>([
  initialSpotState,
  () => {},
]);

export default function SpotProvider({
  initialPlace,
  children,
}: {
  initialPlace?: TChoosePlace;
  children: ReactNode;
}) {
  const initialState = createInitialSpotState(initialPlace);
  const [state, dispatch] = useReducer(reducer, initialState);

  return (
    <SpotContext.Provider value={[state, dispatch]}>
      {children}
    </SpotContext.Provider>
  );
}
