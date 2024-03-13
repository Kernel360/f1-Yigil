'use client';

import { createContext, useReducer } from 'react';
import reducer, { createInitialPlace, defaultPlace } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TChoosePlace } from '../schema';
import type { TPlaceAction, TPlaceState } from './reducer';

export const PlaceContext = createContext<
  [TPlaceState, Dispatch<TPlaceAction>]
>([defaultPlace, () => {}]);

export default function PlaceProvider({
  course,
  place,
  children,
}: {
  course?: boolean;
  place?: TChoosePlace;
  children: ReactNode;
}) {
  const initialPlace = createInitialPlace(course, place);
  const [state, dispatch] = useReducer(reducer, initialPlace);

  return (
    <PlaceContext.Provider value={[state, dispatch]}>
      {children}
    </PlaceContext.Provider>
  );
}
