import type { TStep } from './types';

export const SPOT_STEP_STEP_COUNT = 7;
export const COURSE_FROM_NEW_STEP_COUNT = 7;
export const COURSE_FROM_EXISTING_STEP_COUNT = 6;

export const initialSpotStep: TStep = {
  makingStep: { kind: 'spot', data: { label: '장소 입력', value: 1 } },
  inputStep: { kind: 'from-new', data: { label: '시작', value: 0 } },
};

export const initialCourseFromExistingStep: TStep = {
  makingStep: {
    kind: 'course-existing',
    data: { label: '장소 선택', value: 1 },
  },
  inputStep: {
    kind: 'from-existing',
    data: { label: '시작', value: 0 },
  },
};

export const initialCourseFromNewStep: TStep = {
  makingStep: {
    kind: 'course-new',
    data: { label: '장소 입력', value: 1 },
  },
  inputStep: {
    kind: 'from-new',
    data: { label: '시작', value: 0 },
  },
};

export const endSpotStep: TStep = {
  makingStep: {
    kind: 'spot',
    data: { label: '완료', value: 4 },
  },
  inputStep: {
    kind: 'from-new',
    data: { label: '리뷰', value: 4 },
  },
};

export const endCourseFromExistingStep: TStep = {
  makingStep: {
    kind: 'course-existing',
    data: {
      label: '완료',
      value: 5,
    },
  },
  inputStep: {
    kind: 'from-existing',
    data: {
      label: '리뷰',
      value: 3,
    },
  },
};

export const endCourseFromNewStep: TStep = {
  makingStep: {
    kind: 'course-new',
    data: {
      label: '완료',
      value: 4,
    },
  },
  inputStep: {
    kind: 'from-new',
    data: {
      label: '리뷰',
      value: 4,
    },
  },
};
