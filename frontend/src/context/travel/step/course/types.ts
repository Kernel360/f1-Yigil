export type TCourseWithNewStepState =
  | { label: '방식 선택'; value: 0 }
  | { label: '장소 입력'; value: 1 }
  | { label: '사진 업로드'; value: 2 }
  | { label: '리뷰 입력'; value: 3 }
  | { label: '기록 확정'; value: 4 };

export type TCourseWithoutNewStepState =
  | { label: '방식 선택'; value: 0 }
  | { label: '장소 선택'; value: 1 }
  | { label: '순서 결정'; value: 2 }
  | { label: '리뷰 입력'; value: 3 }
  | { label: '일정 확정'; value: 4 };

export type TCourseStepAction = {
  type: 'NEXT' | 'PREVIOUS';
};
