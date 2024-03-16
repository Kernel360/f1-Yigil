'use client';

import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import { StepContext } from '@/context/travel/step/StepContext';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { CourseContext } from '@/context/travel/course/CourseContext';
import { isDefaultPlace } from '@/context/travel/place/reducer';

import type { TCourseState, TSpotState } from '@/context/travel/schema';
import type { TStepState } from '@/context/travel/step/reducer';
import ToastMsg from '../ui/toast/ToastMsg';

function canGoNext(
  spot: TSpotState,
  course: TCourseState,
  step: TStepState,
): true | string {
  switch (step.data.value) {
    case 0:
      return true;
    case 1: {
      if (
        step.type === 'spot' &&
        isDefaultPlace({ type: 'spot', data: spot.place })
      ) {
        return '장소를 선택해주세요!';
      }

      if (step.type === 'course' && course.spots.length === 0) {
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

export default function Navigation() {
  const [step, dispatch] = useContext(StepContext);
  const [spot, dispatchSpot] = useContext(SpotContext);
  const [course, dispatchCourse] = useContext(CourseContext);
  const [error, setError] = useState('');

  const { back } = useRouter();

  return (
    <nav className="mx-4 flex justify-between items-center">
      <button
        onClick={() => {
          // Spot 추가 / 장소 입력 단계일 때
          if (step.type === 'spot' && step.data.value === 1) {
            if (!isDefaultPlace({ type: 'spot', data: spot.place })) {
              dispatchSpot({ type: 'INIT_SPOT' });
            } else {
              back();
            }

            return;
          }

          // Course 추가 / 장소 입력 단계일 때
          if (step.type === 'course' && step.data.value === 1) {
            const places = course.spots.map((spot) => spot.place);

            if (!isDefaultPlace({ type: 'course', data: places })) {
              dispatchCourse({ type: 'INIT_COURSE' });
              return;
            }
          }

          // Spot 추가 / 방식 선택 단계일 때
          if (step.data.value === 0) {
            back();
            return;
          }

          dispatch({ type: 'PREVIOUS' });
        }}
      >
        이전
      </button>
      <span className="text-xl text-semibold text-gray-900">
        {step.data.label}
      </span>
      {step.data.value === 4 ? (
        <button className="text-blue-500">확정</button>
      ) : (
      <button
        onClick={() => {
          const result = canGoNext(spot, course, step);

          if (result !== true) {
            setError(result);
            return;
          }

          dispatch({ type: 'NEXT' });
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
