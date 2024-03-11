import { describe, expect, test } from 'vitest';

import { initialSpotState, reducer } from '@/context/travel/spot/reducer';
import type {
  TChoosePlace,
  TSpotAction,
  TSpotReview,
} from '@/context/travel/spot/reducer';
import type { TInputImage } from '@/app/_components/images';

const place: TChoosePlace = {
  name: '장소',
  address: '도로명주소',
  mapImageUrl: '지도 이미지',
  coords: { lat: 37.3595704, lng: 127.105399 },
};

const images: TInputImage[] = [
  { filename: '파일명1.jpg', uri: '파일내용1' },
  { filename: '파일명2.jpg', uri: '파일내용2' },
];

const review: TSpotReview = {
  rate: 4,
  content: '리뷰 내용',
};

describe('Reducer success test', () => {
  const actions: TSpotAction[] = [
    { type: 'SET_PLACE', payload: place },
    { type: 'SET_IMAGES', payload: images },
    { type: 'SET_REVIEW', payload: review },
  ];

  test.each(actions)('$type', (action) => {
    const nextState = reducer(initialSpotState, action);

    switch (action.type) {
      case 'SET_PLACE': {
        expect(nextState.place).toEqual(place);
        return;
      }
      case 'SET_IMAGES': {
        expect(nextState.images).toEqual(images);
        return;
      }
      case 'SET_REVIEW': {
        expect(nextState.review).toEqual(review);
        return;
      }
    }
  });
});

describe('Reducer fail test', () => {
  const actions: TSpotAction[] = [
    { type: 'SET_PLACE', payload: {} },
    { type: 'SET_IMAGES', payload: {} },
    { type: 'SET_REVIEW', payload: {} },
  ];

  test.each(actions)('$type', (action) => {
    const nextState = reducer(initialSpotState, action);

    expect(nextState).toEqual(initialSpotState);
  });
});
