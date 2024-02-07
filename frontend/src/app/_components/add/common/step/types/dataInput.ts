type TDataFromNewStep =
  | { label: '시작'; value: 0 }
  | { label: '주소'; value: 1 }
  | { label: '사진'; value: 2 }
  | { label: '별점'; value: 3 }
  | { label: '리뷰'; value: 4 };

type TDataFromExistingStep =
  | { label: '시작'; value: 0 }
  | { label: '순서'; value: 1 }
  | { label: '별점'; value: 2 }
  | { label: '리뷰'; value: 3 };

export type TDataFromNew = {
  kind: 'from-new';
  data: TDataFromNewStep;
};
type TDataFromExisting = {
  kind: 'from-existing';
  data: TDataFromExistingStep;
};

export type TDataInputStep = TDataFromNew | TDataFromExisting;
