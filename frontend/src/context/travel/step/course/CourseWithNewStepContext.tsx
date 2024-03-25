'use client';

import { createContext, useReducer } from 'react';
import reducer from './courseWithNewReducer';

import type { Dispatch, ReactNode } from 'react';
import type { TCourseWithNewStepState, TCourseStepAction } from './types';

export const initialCourseWithNewStepState: TCourseWithNewStepState = {
  label: '방식 선택',
  value: 0,
};

export const CourseWithNewStepContext = createContext<
  [TCourseWithNewStepState, Dispatch<TCourseStepAction>]
>([initialCourseWithNewStepState, () => {}]);

export default function CourseWithNewStepProvider({
  children,
}: {
  children: ReactNode;
}) {
  const [state, dispatch] = useReducer(reducer, initialCourseWithNewStepState);

  return (
    <CourseWithNewStepContext.Provider value={[state, dispatch]}>
      {children}
    </CourseWithNewStepContext.Provider>
  );
}
