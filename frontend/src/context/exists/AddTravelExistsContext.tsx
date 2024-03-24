'use client';

import { createContext, useReducer } from 'react';
import reducer, { defaultAddTravelExistsState } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TAddTravelExistsState, TAddTravelExistsAction } from './reducer';

export const AddTravelExistsContext = createContext<
  [TAddTravelExistsState, Dispatch<TAddTravelExistsAction>]
>([defaultAddTravelExistsState, () => {}]);

export default function AddTravelExistsProvider({
  children,
}: {
  children: ReactNode;
}) {
  const [state, dispatch] = useReducer(reducer, defaultAddTravelExistsState);

  return (
    <AddTravelExistsContext.Provider value={[state, dispatch]}>
      {children}
    </AddTravelExistsContext.Provider>
  );
}
