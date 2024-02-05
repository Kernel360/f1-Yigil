type TDataFromNewStep =
  | { label: '주소'; value: 1 }
  | { label: '사진'; value: 2 }
  | { label: '별점'; value: 3 }
  | { label: '리뷰'; value: 4 };

type TDataFromExistingStep =
  | { label: '순서'; value: 1 }
  | { label: '별점'; value: 2 }
  | { label: '리뷰'; value: 3 };

type TDataFromNew = {
  kind: 'from-new';
  data: TDataFromNewStep;
};
type TDataFromExisting = {
  kind: 'from-existing';
  data: TDataFromExistingStep;
};

export type TDataInputStep = TDataFromNew | TDataFromExisting;
