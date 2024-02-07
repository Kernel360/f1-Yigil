type TSpotStep =
  | { label: '장소 입력'; value: 1 }
  | { label: '정보 입력'; value: 2 }
  | { label: '장소 확정'; value: 3 }
  | { label: '완료'; value: 4 };
type TCourseFromNewStep =
  | { label: '장소 입력'; value: 1 }
  | { label: '정보 입력'; value: 2 }
  | { label: '일정 확정'; value: 3 }
  | { label: '완료'; value: 4 };
type TCourseFromExistingStep =
  | { label: '장소 선택'; value: 1 }
  | { label: '순서 결정'; value: 2 }
  | { label: '정보 입력'; value: 3 }
  | { label: '일정 확정'; value: 4 }
  | { label: '완료'; value: 5 };

type TSpot = {
  kind: 'spot';
  data: TSpotStep;
};

type TCourseFromNew = {
  kind: 'course-new';
  data: TCourseFromNewStep;
};

type TCourseFromExisting = {
  kind: 'course-existing';
  data: TCourseFromExistingStep;
};

export type TMakingStep = TSpot | TCourseFromNew | TCourseFromExisting;
