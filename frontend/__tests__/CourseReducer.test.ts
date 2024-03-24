import { describe, expect, test } from 'vitest';

import reducer, { initialCourseState } from '@/context/travel/course/reducer';

import type { TInputImage } from '@/app/_components/images';
import type { TCourseAction } from '@/context/travel/course/reducer';
import type { TChoosePlace, TReview } from '@/context/travel/schema';

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

describe('Course reducer success test', () => {
  test('ADD_SPOT', () => {
    const nextState = reducer(initialCourseState, {
      type: 'ADD_SPOT',
      payload: place,
    });

    expect(nextState.spots[0].place).toEqual(place);
  });

  test('REMOVE_SPOT', () => {
    const currentCourseState = initialCourseState;
    currentCourseState.spots.push({
      place,
      images: { type: 'new', data: images },
      review,
    });

    const nextState = reducer(currentCourseState, {
      type: 'REMOVE_SPOT',
      payload: currentCourseState.spots[0],
    });

    expect(nextState.spots).toEqual([]);
  });
});

describe('Course reducer fail test', () => {
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
