'use client';

import { createContext, useReducer } from 'react';
import reducer, { initialCourseState } from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TCourseState } from '../schema';
import type { TCourseAction } from './reducer';

export const CourseContext = createContext<
  [TCourseState, Dispatch<TCourseAction>]
>([initialCourseState, () => {}]);

export default function SpotProvider({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(reducer, initialCourseState);

  return (
    <CourseContext.Provider value={[state, dispatch]}>
      {children}
    </CourseContext.Provider>
  );
}
