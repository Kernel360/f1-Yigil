import { describe, expect, test } from 'vitest';

import reducer, { initialCourseState } from '@/context/travel/course/reducer';

import type { TCourseAction } from '@/context/travel/course/reducer';
import type {
  TChoosePlace,
  TReview,
  TSpotState,
} from '@/context/travel/schema';
import type { TInputImage } from '@/app/_components/images';
import { isEqualSpot } from '@/context/travel/utils';

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

const review: TReview = {
  rate: 4,
  content: '리뷰 내용',
};

const spot: TSpotState = {
  place,
  images,
  review,
};

describe('Course reducer success test', () => {
  const actions: TCourseAction[] = [];

  test('ADD_SPOT', () => {
    const nextState = reducer(initialCourseState, {
      type: 'ADD_SPOT',
      payload: spot,
    });

    expect(nextState.spots[0]).toEqual(spot);
  });

  test('REMOVE_SPOT', () => {
    const currentCourseState = initialCourseState;
    currentCourseState.spots.push(spot);

    const nextState = reducer(currentCourseState, {
      type: 'REMOVE_SPOT',
      payload: spot,
    });

    expect(nextState.spots).toEqual([]);
  });

  test.each(actions)('$type', (action) => {
    const nextState = reducer(initialCourseState, action);

    expect(nextState.spots[0]).toEqual(spot);
  });
});

describe('Reducer fail test', () => {
  const actions: TCourseAction[] = [
    { type: 'CHANGE_SPOT_ORDER', payload: {} },
    { type: 'ADD_SPOT', payload: {} },
    { type: 'REMOVE_SPOT', payload: {} },
    { type: 'SET_SPOT_PLACE', payload: {} },
    { type: 'SET_SPOT_IMAGES', payload: {} },
    { type: 'SET_SPOT_REVIEW', payload: {} },
    { type: 'SET_COURSE_REVIEW', payload: {} },
  ];

  test.each(actions)('$type', (action) => {
    const nextState = reducer(initialCourseState, action);

    expect(nextState).toEqual(initialCourseState);
  });
});
