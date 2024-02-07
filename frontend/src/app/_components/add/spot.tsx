import InfoTitle from './common/InfoTitle';
import AddPlaceInfo from './common/AddPlaceInto';
import { ImageHandler } from '../images';
import PostRating from './common/PostRating';
import PostReview from './common/PostReview';
import SearchBox from '../search';

import { Dispatch, SetStateAction } from 'react';
import type { DataInput, Making } from './common/step/types';

function switchWithDataInput(
  dataInput: DataInput.TDataFromNew,
  name: string,
  address: string,
  reviewState: { title?: string; review: string },
  setReviewState: Dispatch<SetStateAction<{ title?: string; review: string }>>,
) {
  const { data } = dataInput;
  const label = data.label;

  switch (label) {
    case '시작': {
      return <></>;
    }
    case '주소': {
      return (
        <>
          <InfoTitle label={label} additionalLabel="를 확인하세요" />
          <AddPlaceInfo name={name} address={address} />
        </>
      );
    }
    case '사진': {
      return (
        <>
          <InfoTitle label={label} additionalLabel="을 업로드하세요" />
          <ImageHandler size={5} />
        </>
      );
    }
    case '별점': {
      return (
        <>
          <InfoTitle label={label} additionalLabel="을 매기세요" />
          <PostRating />
        </>
      );
    }
    case '리뷰': {
      return (
        <>
          <InfoTitle key={label} label={label} additionalLabel="를 남기세요" />
          <PostReview
            reviewState={reviewState}
            setReviewState={setReviewState}
          />
        </>
      );
    }
  }
}

export function switchAddSpot(
  name: string,
  address: string,
  makingStep: Making.TSpot,
  dataInput: DataInput.TDataFromNew,
  reviewState: { title?: string; review: string },
  setReviewState: Dispatch<SetStateAction<{ title?: string; review: string }>>,
) {
  const { data } = makingStep;
  const label = data.label;

  switch (label) {
    case '장소 입력': {
      return (
        <>
          <SearchBox />
        </>
      );
    }

    case '정보 입력': {
      return switchWithDataInput(
        dataInput as DataInput.TDataFromNew,
        name,
        address,
        reviewState,
        setReviewState,
      );
    }

    case '장소 확정': {
      return <></>;
    }
    case '완료': {
      return <></>;
    }
  }
}
