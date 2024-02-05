import StepNavigation from './StepNavigation';

import {
  initialSpotStep,
  initialCourseFromNewStep,
  initialCourseFromExistingStep,
} from './constants';

import type { TStep, Making, DataInput } from './types';

/**
 * useReducer에 제공할 reducer
 */
export function reducer(
  step: TStep,
  action: { type: 'next' } | { type: 'previous' },
): TStep {
  switch (action.type) {
    case 'next': {
      return makeNextStep(step);
    }
    case 'previous': {
      return makePreviousStep(step);
    }
  }
}

/**
 * 초기 상태 생성 함수
 */
export function makeInitialStep(
  makingSpot: boolean,
  fromExisting: boolean,
): TStep {
  if (makingSpot) {
    return initialSpotStep;
  }

  if (fromExisting) {
    return initialCourseFromExistingStep;
  }

  return initialCourseFromNewStep;
}

function makeNextStep(step: TStep): TStep {
  const { makingStep, inputStep } = step;

  if (makingStep.data.label === '정보 입력') {
    return {
      makingStep: nextMakingStep(makingStep, inputStep),
      inputStep: nextInputStep(makingStep, inputStep),
    };
  }

  if (makingStep.data.label === '순서 결정') {
    return {
      makingStep: nextMakingStep(makingStep, inputStep),
      inputStep: nextInputStep(makingStep, inputStep),
    };
  }

  return {
    makingStep: nextMakingStep(makingStep, inputStep),
    inputStep,
  };
}

function makePreviousStep(step: TStep): TStep {
  const { makingStep, inputStep } = step;

  if (makingStep.data.label === '정보 입력') {
    return {
      makingStep: previousMakingStep(makingStep, inputStep),
      inputStep: previousInputStep(makingStep, inputStep),
    };
  }

  return {
    makingStep: previousMakingStep(makingStep, inputStep),
    inputStep,
  };
}

function nextMakingStep(
  step: Making.TMakingStep,
  dataInput: DataInput.TDataInputStep,
): Making.TMakingStep {
  const { kind, data } = step;

  switch (kind) {
    case 'spot': {
      if (data.label === '장소 입력') {
        return { kind, data: { label: '정보 입력', value: 2 } };
      }
      if (data.label === '정보 입력') {
        if (dataInput.data.label === '리뷰') {
          return { kind, data: { label: '장소 확정', value: 3 } };
        }
        return { kind, data };
      }
      if (data.label === '장소 확정') {
        return { kind, data: { label: '완료', value: 4 } };
      }
      return { kind, data };
    }
    case 'course-new': {
      if (data.label === '장소 입력') {
        return { kind, data: { label: '정보 입력', value: 2 } };
      }
      if (data.label === '정보 입력') {
        if (dataInput.data.label === '리뷰') {
          return { kind, data: { label: '일정 확정', value: 3 } };
        }
        return { kind, data };
      }
      if (data.label === '일정 확정') {
        return { kind, data: { label: '완료', value: 4 } };
      }
      return { kind, data };
    }
    case 'course-existing': {
      if (data.label === '장소 선택') {
        return { kind, data: { label: '순서 결정', value: 2 } };
      }
      if (data.label === '순서 결정') {
        return { kind, data: { label: '정보 입력', value: 3 } };
      }
      if (data.label === '정보 입력') {
        if (dataInput.data.label === '리뷰') {
          return { kind, data: { label: '일정 확정', value: 4 } };
        }
        return { kind, data };
      }
      if (data.label === '일정 확정') {
        return { kind, data: { label: '완료', value: 5 } };
      }
      return { kind, data };
    }
  }
}

function nextInputStep(
  step: Making.TMakingStep,
  dataInput: DataInput.TDataInputStep,
): DataInput.TDataInputStep {
  const { kind, data } = dataInput;

  switch (kind) {
    case 'from-new': {
      if (data.label === '주소') {
        return { kind, data: { label: '사진', value: 2 } };
      }
      if (data.label === '사진') {
        return { kind, data: { label: '별점', value: 3 } };
      }
      if (data.label === '별점') {
        return { kind, data: { label: '리뷰', value: 4 } };
      }
      return { kind, data };
    }
    case 'from-existing': {
      if (data.label === '순서') {
        if (step.data.label === '순서 결정') {
          return { kind, data: { label: '별점', value: 2 } };
        }

        return { kind, data };
      }
      if (data.label === '별점') {
        return { kind, data: { label: '리뷰', value: 3 } };
      }
      return { kind, data };
    }
  }
}

function previousMakingStep(
  step: Making.TMakingStep,
  dataInput: DataInput.TDataInputStep,
): Making.TMakingStep {
  const { kind, data } = step;

  switch (kind) {
    case 'spot': {
      if (data.label === '완료') {
        return { kind, data: { label: '장소 확정', value: 3 } };
      }
      if (data.label === '장소 확정') {
        return { kind, data: { label: '정보 입력', value: 2 } };
      }
      if (data.label === '정보 입력') {
        if (dataInput.data.label === '주소') {
          return { kind, data: { label: '장소 입력', value: 1 } };
        }
      }
      return { kind, data };
    }
    case 'course-new': {
      if (data.label === '완료') {
        return { kind, data: { label: '일정 확정', value: 3 } };
      }
      if (data.label === '일정 확정') {
        return { kind, data: { label: '정보 입력', value: 2 } };
      }
      if (data.label === '정보 입력') {
        if (dataInput.data.label === '주소') {
          return { kind, data: { label: '장소 입력', value: 1 } };
        }
        return { kind, data };
      }
      return { kind, data };
    }
    case 'course-existing': {
      if (data.label === '완료') {
        return { kind, data: { label: '일정 확정', value: 4 } };
      }
      if (data.label === '일정 확정') {
        return { kind, data: { label: '정보 입력', value: 3 } };
      }
      if (data.label === '정보 입력') {
        if (dataInput.data.label === '별점') {
          return { kind, data: { label: '순서 결정', value: 2 } };
        }
        return { kind, data };
      }
      if (data.label === '순서 결정') {
        return { kind, data: { label: '장소 선택', value: 1 } };
      }
      return { kind, data };
    }
  }
}

function previousInputStep(
  step: Making.TMakingStep,
  dataInput: DataInput.TDataInputStep,
): DataInput.TDataInputStep {
  const { kind, data } = dataInput;

  switch (kind) {
    case 'from-new': {
      if (data.label === '리뷰') {
        return { kind, data: { label: '별점', value: 3 } };
      }
      if (data.label === '별점') {
        return { kind, data: { label: '사진', value: 2 } };
      }
      if (data.label === '사진') {
        return { kind, data: { label: '주소', value: 1 } };
      }
      return { kind, data };
    }
    case 'from-existing': {
      if (data.label === '리뷰') {
        return { kind, data: { label: '별점', value: 2 } };
      }
      if (data.label === '별점') {
        return { kind, data: { label: '순서', value: 1 } };
      }
      return { kind, data };
    }
  }
}

export { StepNavigation };
