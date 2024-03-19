'use client';

import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import { CourseContext } from '@/context/travel/course/CourseContext';
import ToastMsg from '../../ui/toast/ToastMsg';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { getCourseStaticMap, getRouteGeoJson } from './action';

import type { Dispatch } from 'react';
import type {
  TCourseStepAction,
  TCourseWithNewStepState,
  TCourseWithoutNewStepState,
} from '@/context/travel/step/course/types';
import type { TCourseState } from '@/context/travel/schema';
import type { TCourseAction } from '@/context/travel/course/reducer';

async function canGoNext(
  course: TCourseState,
  dispatch: Dispatch<TCourseAction>,
  step: TCourseWithNewStepState | TCourseWithoutNewStepState,
): Promise<true | string> {
  switch (step.value) {
    case 0: {
      return true;
    }
    case 1: {
      if (course.spots.length < 2) {
        return '장소를 선택해주세요!';
      }

      const path = await getRouteGeoJson(course.spots);

      if (path.status === 'failed') {
        return path.message;
      }

      dispatch({ type: 'SET_PATH', payload: path.data });

      return true;
    }
    case 2:
      if (course.spots.some((spot) => spot.images.length === 0)) {
        return '각 장소에 적어도 하나의 사진을 올려주세요!';
      }

      return true;
    case 3:
      if (
        course.spots.some((spot) => spot.review.content === '') ||
        course.review.content === ''
      ) {
        return '각 장소와 코스 전체에 대한 리뷰를 작성해주세요!';
      }

      if (course.review.title === undefined || course.review.title === '') {
        return '코스 제목을 작성해주세요!';
      }

      const staticMapImageUrl = await getCourseStaticMap(
        course.spots.map(({ place }) => place.coords),
      );

      if (staticMapImageUrl.status === 'failed') {
        return staticMapImageUrl.message;
      }

      dispatch({
        type: 'SET_COURSE_STATIC_MAP',
        payload: staticMapImageUrl.dataUrl,
      });

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
          onClick={async () => {
            const result = await canGoNext(course, dispatchCourse, step);

            if (result !== true) {
              setError(result);
              setTimeout(() => setError(''), 2000);
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
