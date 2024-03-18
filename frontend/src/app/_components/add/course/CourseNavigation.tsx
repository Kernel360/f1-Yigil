'use client';

import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import { CourseContext } from '@/context/travel/course/CourseContext';
import { isDefaultPlace } from '@/context/travel/place/reducer';
import ToastMsg from '../../ui/toast/ToastMsg';

import type { Dispatch } from 'react';
import type {
  TCourseStepAction,
  TCourseWithNewStepState,
  TCourseWithoutNewStepState,
} from '@/context/travel/step/course/types';
import { TCourseState } from '@/context/travel/schema';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

function canGoNext(
  course: TCourseState,
  step: TCourseWithNewStepState | TCourseWithoutNewStepState,
): true | string {
  switch (step.value) {
    case 0: {
      return true;
    }
    case 1: {
      if (course.spots.length === 0) {
        return '장소를 선택해주세요!';
      }

      return true;
    }
    case 2:
      return true;
    case 3:
      return true;
    case 4:
      return true;
  }
}

export default function CourseNavigation({
  step,
  dispatchStep,
}: {
  step: TCourseWithNewStepState | TCourseWithoutNewStepState;
  dispatchStep: Dispatch<TCourseStepAction>;
}) {
  const [travelMapState, dispatchTravelMapState] =
    useContext(AddTravelMapContext);
  const [course, dispatchCourse] = useContext(CourseContext);
  const [error, setError] = useState('');

  const { back } = useRouter();

  return (
    <nav className="mx-4 flex justify-between items-center">
      <button
        onClick={() => {
          if (step.value === 1) {
            const places = course.spots.map((spot) => spot.place);

            if (places.length === 0) {
              dispatchStep({ type: 'PREVIOUS' });
              return;
            }

            dispatchTravelMapState({ type: 'OPEN_MAP' });
            return;
          }

          if (step.value === 0) {
            back();
            return;
          }

          dispatchStep({ type: 'PREVIOUS' });
        }}
      >
        이전
      </button>
      <span className="text-xl text-semibold text-gray-900">{step.label}</span>
      {step.value === 4 ? (
        <button className="text-blue-500">확정</button>
      ) : (
        <button
          onClick={() => {
            const result = canGoNext(course, step);

            if (result !== true) {
              setError(result);
              return;
            }

            dispatchStep({ type: 'NEXT' });
          }}
        >
          다음
        </button>
      )}
      <div className="absolute">
        {error && <ToastMsg title={error} id={Date.now()} timer={2000} />}
      </div>
    </nav>
  );
}
