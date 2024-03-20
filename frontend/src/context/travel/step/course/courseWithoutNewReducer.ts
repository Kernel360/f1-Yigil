import type { TCourseWithoutNewStepState, TCourseStepAction } from './types';

export default function reducer(
  state: TCourseWithoutNewStepState,
  action: TCourseStepAction,
): TCourseWithoutNewStepState {
  switch (action.type) {
    case 'NEXT': {
      switch (state.value) {
        case 0: {
          return { label: '장소 선택', value: 1 };
        }
        case 1: {
          return { label: '순서 결정', value: 2 };
        }
        case 2: {
          return { label: '리뷰 입력', value: 3 };
        }
        case 3: {
          return { label: '일정 확정', value: 4 };
        }
        case 4: {
          return state;
        }
      }
    }
    case 'PREVIOUS': {
      switch (state.value) {
        case 0: {
          return state;
        }
        case 1: {
          return { label: '방식 선택', value: 0 };
        }
        case 2: {
          return { label: '장소 선택', value: 1 };
        }
        case 3: {
          return { label: '순서 결정', value: 2 };
        }
        case 4: {
          return { label: '리뷰 입력', value: 3 };
        }
      }
    }
  }
}
