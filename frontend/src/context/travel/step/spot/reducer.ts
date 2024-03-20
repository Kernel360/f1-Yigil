export type TSpotStepState =
  | { label: '장소 입력'; value: 1 }
  | { label: '사진 업로드'; value: 2 }
  | { label: '리뷰 입력'; value: 3 }
  | { label: '기록 확정'; value: 4 };

export const initialSpotStepState: TSpotStepState = {
  label: '장소 입력',
  value: 1,
};

export type TSpotStepAction = {
  type: 'NEXT' | 'PREVIOUS';
};

export default function reducer(
  state: TSpotStepState,
  action: TSpotStepAction,
): TSpotStepState {
  switch (action.type) {
    case 'NEXT': {
      switch (state.value) {
        case 1: {
          return { label: '사진 업로드', value: 2 };
        }
        case 2: {
          return { label: '리뷰 입력', value: 3 };
        }
        case 3: {
          return { label: '기록 확정', value: 4 };
        }
        case 4: {
          return state;
        }
      }
    }
    case 'PREVIOUS': {
      switch (state.value) {
        case 1: {
          return state;
        }
        case 2: {
          return { label: '장소 입력', value: 1 };
        }
        case 3: {
          return { label: '사진 업로드', value: 2 };
        }
        case 4: {
          return { label: '리뷰 입력', value: 3 };
        }
      }
    }
  }
}
