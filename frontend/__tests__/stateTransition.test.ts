import { describe, expect, test } from 'vitest';

import { makeInitialStep, reducer } from '@/app/_components/add/common/step';

import {
  SPOT_STEP_STEP_COUNT,
  COURSE_FROM_NEW_STEP_COUNT,
  COURSE_FROM_EXISTING_STEP_COUNT,
  initialSpotStep,
  initialCourseFromNewStep,
  initialCourseFromExistingStep,
  endSpotStep,
  endCourseFromNewStep,
  endCourseFromExistingStep,
} from '@/app/_components/add/common/step/constants';

vi.mock('next/headers', async () => {
  return {
    cookies: () => {
      return {
        get: () => {
          return {
            value: 'cookie',
          };
        },
      };
    },
  };
});

describe('test', () => {
  const initialStates = [
    { makingSpot: true, fromExisting: false, label: '장소' },
    { makingSpot: false, fromExisting: false, label: '일정만 기록하기' },
    { makingSpot: false, fromExisting: true, label: '장소도 함께 기록하기' },
  ];

  test.each(initialStates)('$label', ({ makingSpot, fromExisting }) => {
    const initialStep = makeInitialStep(makingSpot, fromExisting);
  });
});

describe('초기 상태 생성', () => {
  const initialStates = [
    { makingSpot: true, fromExisting: false, label: '장소' },
    { makingSpot: false, fromExisting: false, label: '일정만 기록하기' },
    { makingSpot: false, fromExisting: true, label: '장소도 함께 기록하기' },
  ];

  test.each(initialStates)('$label', ({ makingSpot, fromExisting }) => {
    const initialStep = makeInitialStep(makingSpot, fromExisting);

    if (makingSpot) {
      expect(initialStep).toEqual(initialSpotStep);
    } else {
      if (fromExisting) {
        expect(initialStep).toEqual(initialCourseFromExistingStep);
      } else {
        expect(initialStep).toEqual(initialCourseFromNewStep);
      }
    }
  });
});

describe('다음 상태 전이 테스트', () => {
  test('장소 추가 단계 처음부터 끝까지', () => {
    let step = makeInitialStep(true, false);

    // Exclude start
    for (let i = 0; i < SPOT_STEP_STEP_COUNT - 1; i++) {
      step = reducer(step, { type: 'next' });
    }

    expect(step).toEqual(endSpotStep);
  });

  test('일정만 기록하기 단계 처음부터 끝까지', () => {
    let step = makeInitialStep(false, true);

    // Exclude start
    for (let i = 0; i < COURSE_FROM_EXISTING_STEP_COUNT - 1; i++) {
      step = reducer(step, { type: 'next' });
    }

    expect(step).toEqual(endCourseFromExistingStep);
  });

  test('장소도 함께 기록하기 처음부터 끝까지', () => {
    let step = makeInitialStep(false, false);

    // Exclude start
    for (let i = 0; i < COURSE_FROM_NEW_STEP_COUNT - 1; i++) {
      step = reducer(step, { type: 'next' });
    }

    expect(step).toEqual(endCourseFromNewStep);
  });
});

describe('이전 상태 전이 테스트', () => {
  test('장소 추가 단계 끝부터 처음까지', () => {
    let step = endSpotStep;

    // Exclude start
    for (let i = 0; i < SPOT_STEP_STEP_COUNT - 1; i++) {
      step = reducer(step, { type: 'previous' });
    }

    expect(step).toEqual(initialSpotStep);
  });

  test('일정만 기록하기 단계 끝부터 처음까지', () => {
    let step = endCourseFromExistingStep;

    // Exclude start
    for (let i = 0; i < COURSE_FROM_EXISTING_STEP_COUNT - 1; i++) {
      step = reducer(step, { type: 'previous' });
    }

    expect(step).toEqual(initialCourseFromExistingStep);
  });

  test('장소도 함께 기록하기 단계 끝부터 처음까지', () => {
    let step = endCourseFromNewStep;

    // Exclude start
    for (let i = 0; i < COURSE_FROM_NEW_STEP_COUNT - 1; i++) {
      step = reducer(step, { type: 'previous' });
    }

    expect(step).toEqual(initialCourseFromNewStep);
  });
});
