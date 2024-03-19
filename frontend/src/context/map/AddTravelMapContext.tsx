'use client';

import { createContext, useReducer } from 'react';
import reducer, { defaultAddTravelMapState } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TAddTravelMapAction, TAddTravelMapState } from './reducer';

export const AddTravelMapContext = createContext<
  [TAddTravelMapState, Dispatch<TAddTravelMapAction>]
>([defaultAddTravelMapState, () => {}]);

export default function AddTravelMapProvider({
  children,
}: {
  children: ReactNode;
}) {
  const [state, dispatch] = useReducer(reducer, defaultAddTravelMapState);

  return (
    <AddTravelMapContext.Provider value={[state, dispatch]}>
      {children}
    </AddTravelMapContext.Provider>
  );
}
