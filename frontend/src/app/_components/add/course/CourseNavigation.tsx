'use client';

import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { AddTravelExistsContext } from '@/context/exists/AddTravelExistsContext';
import { CourseContext } from '@/context/travel/course/CourseContext';
import { CourseWithNewStepContext } from '@/context/travel/step/course/CourseWithNewStepContext';
import { CourseWithoutNewStepContext } from '@/context/travel/step/course/CourseWithoutNewStepContext';

import ToastMsg from '@/app/_components/ui/toast/ToastMsg';
import Dialog from '@/app/_components/ui/dialog/Dialog';

import {
  getCourseStaticMap,
  getRouteGeoJson,
  getSelectedSpots,
  postCourse,
} from './action';

import type { Dispatch } from 'react';
import type {
  TCourseStepAction,
  TCourseWithNewStepState,
  TCourseWithoutNewStepState,
} from '@/context/travel/step/course/types';
import type { TCourseState } from '@/context/travel/schema';
import type { TCourseAction } from '@/context/travel/course/reducer';
import type { TAddTravelExistsState } from '@/context/exists/reducer';

async function canGoNext(
  method: 'with-new' | 'without-new',
  course: TCourseState,
  dispatch: Dispatch<TCourseAction>,
  step: TCourseWithNewStepState | TCourseWithoutNewStepState,
  exists: TAddTravelExistsState,
): Promise<true | string> {
  switch (step.value) {
    case 0: {
      return true;
    }
    case 1: {
      if (method === 'without-new') {
        const { selectedSpotsId } = exists;

        if (selectedSpotsId.length < 2) {
          return '장소를 두 곳 이상 선택해주세요!';
        }

        const selected = await getSelectedSpots(selectedSpotsId);

        if (selected.status === 'failed') {
          console.error(
            `${selected.code ? `${selected.code} - ` : ''}${selected.message}`,
          );
          return selected.message;
        }

        dispatch({ type: 'SET_EXISTING_SPOTS', payload: selected.data });
        return true;
      }

      if (course.spots.length < 2) {
        return '장소를 두 곳 이상 선택해주세요!';
      }

      return true;
    }
    case 2: {
      if (method === 'with-new') {
        if (course.spots.some((spot) => spot.images.data.length === 0)) {
          return '각 장소에 적어도 하나의 사진을 올려주세요!';
        }
      }

      return true;
    }
    case 3:
      if (method === 'with-new') {
        if (
          course.spots.some((spot) => spot.review.content.trim() === '') ||
          course.review.content.trim() === ''
        ) {
          return '각 장소와 코스 전체에 대한 리뷰를 작성해주세요!';
        }
      }

      if (
        course.review.title === undefined ||
        course.review.title.trim() === ''
      ) {
        return '코스 제목을 작성해주세요!';
      }

      const path = await getRouteGeoJson(course.spots);

      if (path.status === 'failed') {
        return path.message;
      }

      dispatch({ type: 'SET_PATH', payload: path.data });

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

function getCurrentStep(
  method: 'with-new' | 'without-new',
  withNew: TCourseWithNewStepState,
  withoutNew: TCourseWithoutNewStepState,
) {
  if (method === 'with-new') {
    return withNew;
  }

  return withoutNew;
}

function getCurrentDispatchStep(
  method: 'with-new' | 'without-new',
  withNew: Dispatch<TCourseStepAction>,
  withoutNew: Dispatch<TCourseStepAction>,
) {
  if (method === 'with-new') {
    return withNew;
  }

  return withoutNew;
}

export default function CourseNavigation({
  method,
}: {
  method: 'with-new' | 'without-new';
}) {
  const [course, dispatchCourse] = useContext(CourseContext);

  const [, dispatchTravelMapState] = useContext(AddTravelMapContext);
  const [addTravelExists, dispatchAddTravelExists] = useContext(
    AddTravelExistsContext,
  );

  const [withNew, dispatchWithNew] = useContext(CourseWithNewStepContext);
  const [withoutNew, dispatchWithoutNew] = useContext(
    CourseWithoutNewStepContext,
  );

  const currentStep = getCurrentStep(method, withNew, withoutNew);
  const currentDispatchStep = getCurrentDispatchStep(
    method,
    dispatchWithNew,
    dispatchWithoutNew,
  );

  const [error, setError] = useState('');
  const [isOpen, setIsOpen] = useState(false);

  const { back, push } = useRouter();

  function openDialog() {
    setIsOpen(true);
  }

  function closeDialog() {
    setIsOpen(false);
  }

  async function handleConfirm() {
    const result = await postCourse(course);

    if (result.status === 'failed') {
      setError(result.message);
      setTimeout(() => setError(''), 2000);
      return;
    }

    push('/add/course/confirm');
  }

  return (
    <nav className="mx-4 flex justify-between items-center">
      <button
        onClick={() => {
          if (currentStep.value === 0) {
            back();
            return;
          }

          if (currentStep.value === 1) {
            if (method === 'with-new') {
              const places = course.spots.map((spot) => spot.place);

              if (places.length === 0) {
                currentDispatchStep({ type: 'PREVIOUS' });
                return;
              }

              dispatchTravelMapState({ type: 'OPEN_MAP' });
              return;
            }

            if (method === 'without-new') {
              dispatchCourse({ type: 'INIT_COURSE' });
              dispatchAddTravelExists({ type: 'INIT' });
            }
          }

          currentDispatchStep({ type: 'PREVIOUS' });
        }}
      >
        이전
      </button>
      <span className="text-xl text-semibold text-gray-900">
        {currentStep.label}
      </span>
      {currentStep.value === 4 ? (
        <button className="text-blue-500" onClick={openDialog}>
          확정
        </button>
      ) : (
        <button
          onClick={async () => {
            const result = await canGoNext(
              method,
              course,
              dispatchCourse,
              currentStep,
              addTravelExists,
            );

            if (result !== true) {
              setError(result);
              setTimeout(() => setError(''), 2000);
              return;
            }

            currentDispatchStep({ type: 'NEXT' });
          }}
        >
          다음
        </button>
      )}
      {isOpen && (
        <Dialog
          text="코스를 확정하시겠나요?"
          loadingText="코스 기록 중..."
          closeModal={closeDialog}
          handleConfirm={handleConfirm}
        />
      )}
      <div className="absolute">
        {error && <ToastMsg title={error} id={Date.now()} timer={2000} />}
      </div>
    </nav>
  );
}
