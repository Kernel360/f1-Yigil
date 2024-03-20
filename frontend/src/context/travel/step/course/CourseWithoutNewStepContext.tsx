'use client';

import { createContext, useReducer } from 'react';
import reducer from './courseWithoutNewReducer';

import type { Dispatch, ReactNode } from 'react';
import type { TCourseWithoutNewStepState, TCourseStepAction } from './types';

export const initialCourseWithNewStepState: TCourseWithoutNewStepState = {
  label: '방식 선택',
  value: 0,
};

export const CourseWithoutNewStepContext = createContext<
  [TCourseWithoutNewStepState, Dispatch<TCourseStepAction>]
>([initialCourseWithNewStepState, () => {}]);

export default function CourseWithoutNewStepProvider({
  children,
}: {
  children: ReactNode;
}) {
  const [state, dispatch] = useReducer(reducer, initialCourseWithNewStepState);

  return (
    <CourseWithoutNewStepContext.Provider value={[state, dispatch]}>
      {children}
    </CourseWithoutNewStepContext.Provider>
  );
}
