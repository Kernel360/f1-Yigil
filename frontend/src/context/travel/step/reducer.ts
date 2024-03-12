import type { TChoosePlace } from '../schema';

export type TStepState =
  | {
      type: 'spot';
      data:
        | { label: '장소 선택'; value: 1 }
        | { label: '사진 업로드'; value: 2 }
        | { label: '리뷰 입력'; value: 3 }
        | { label: '기록 확정'; value: 4 };
    }
  | {
      type: 'course';
      data:
        | { label: '방식 선택'; value: 0 }
        | { label: '장소 선택'; value: 1 }
        | { label: '사진 업로드'; value: 2 }
        | { label: '리뷰 입력'; value: 3 }
        | { label: '기록 확정'; value: 4 };
    };

export const defaultStepState: TStepState = {
  type: 'spot',
  data: {
    label: '장소 선택',
    value: 1,
  },
};

export type TStepAction = {
  type: 'NEXT' | 'PREVIOUS';
};

export function createInitialStep(course?: boolean): TStepState {
  if (course) {
    return { type: 'course', data: { label: '방식 선택', value: 0 } };
  }

  return { type: 'spot', data: { label: '장소 선택', value: 1 } };
}

export default function reducer(
  state: TStepState,
  action: TStepAction,
): TStepState {
  if (state.type === 'spot') {
    return spotReducer(state, action);
  }

  return courseReducer(state, action);
}

function spotReducer(state: TStepState, action: TStepAction): TStepState {
  if (state.type === 'course') {
    return state;
  }

  switch (action.type) {
    case 'NEXT': {
      switch (state.data.value) {
        case 1: {
          return { ...state, data: { label: '사진 업로드', value: 2 } };
        }
        case 2: {
          return { ...state, data: { label: '리뷰 입력', value: 3 } };
        }
        case 3: {
          return { ...state, data: { label: '기록 확정', value: 4 } };
        }
        case 4: {
          return state;
        }
      }
    }
    case 'PREVIOUS': {
      switch (state.data.value) {
        case 1: {
          return { ...state };
        }
        case 2: {
          return { ...state, data: { label: '장소 선택', value: 1 } };
        }
        case 3: {
          return { ...state, data: { label: '사진 업로드', value: 2 } };
        }
        case 4: {
          return { ...state, data: { label: '리뷰 입력', value: 3 } };
        }
      }
    }
  }
}

function courseReducer(state: TStepState, action: TStepAction): TStepState {
  if (state.type === 'spot') {
    return state;
  }

  switch (action.type) {
    case 'NEXT': {
      switch (state.data.value) {
        case 0: {
          return { ...state, data: { label: '장소 선택', value: 1 } };
        }
        case 1: {
          return { ...state, data: { label: '사진 업로드', value: 2 } };
        }
        case 2: {
          return { ...state, data: { label: '리뷰 입력', value: 3 } };
        }
        case 3: {
          return { ...state, data: { label: '기록 확정', value: 4 } };
        }
        case 4: {
          return state;
        }
      }
    }
    case 'PREVIOUS': {
      switch (state.data.value) {
        case 0: {
          return state;
        }
        case 1: {
          return { ...state, data: { label: '방식 선택', value: 0 } };
        }
        case 2: {
          return { ...state, data: { label: '장소 선택', value: 1 } };
        }
        case 3: {
          return { ...state, data: { label: '사진 업로드', value: 2 } };
        }
        case 4: {
          return { ...state, data: { label: '리뷰 입력', value: 3 } };
        }
      }
    }
  }
}
