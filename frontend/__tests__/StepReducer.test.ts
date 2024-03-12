import { describe, expect, test } from 'vitest';

import reducer, { createInitialStep } from '@/context/travel/step/reducer';
import type { TChoosePlace } from '@/context/travel/schema';

const place: TChoosePlace = {
  name: '장소',
  address: '도로명주소',
  mapImageUrl: '지도 이미지',
  coords: { lat: 37.3595704, lng: 127.105399 },
};

describe('Step reducer NEXT success test', () => {
  test('spot', () => {
    let spotStep = createInitialStep();
    expect(spotStep.data.label).toEqual('장소 입력');

    spotStep = reducer(spotStep, { type: 'NEXT' });
    expect(spotStep.data.label).toEqual('사진 업로드');

    spotStep = reducer(spotStep, { type: 'NEXT' });
    expect(spotStep.data.label).toEqual('리뷰 입력');

    spotStep = reducer(spotStep, { type: 'NEXT' });
    expect(spotStep.data.label).toEqual('기록 확정');

    spotStep = reducer(spotStep, { type: 'NEXT' });
    expect(spotStep.data.label).toEqual('기록 확정');
  });

  let courseStep = createInitialStep(true);

  test('course', () => {
    courseStep = reducer(courseStep, { type: 'NEXT' });
    expect(courseStep.data.label).toEqual('장소 입력');

    courseStep = reducer(courseStep, { type: 'NEXT' });
    expect(courseStep.data.label).toEqual('사진 업로드');

    courseStep = reducer(courseStep, { type: 'NEXT' });
    expect(courseStep.data.label).toEqual('리뷰 입력');

    courseStep = reducer(courseStep, { type: 'NEXT' });
    expect(courseStep.data.label).toEqual('기록 확정');

    courseStep = reducer(courseStep, { type: 'NEXT' });
    expect(courseStep.data.label).toEqual('기록 확정');
  });
});

describe('Step reducer PREVIOUS success test', () => {
  let spotStep = createInitialStep();
  for (let i = 0; i < 4; i += 1) {
    spotStep = reducer(spotStep, { type: 'NEXT' });
  }

  let courseStep = createInitialStep(true);
  for (let i = 0; i < 5; i += 1) {
    courseStep = reducer(courseStep, { type: 'NEXT' });
  }

  test('spot', () => {
    expect(spotStep.data.label).toEqual('기록 확정');

    spotStep = reducer(spotStep, { type: 'PREVIOUS' });
    expect(spotStep.data.label).toEqual('리뷰 입력');

    spotStep = reducer(spotStep, { type: 'PREVIOUS' });
    expect(spotStep.data.label).toEqual('사진 업로드');

    spotStep = reducer(spotStep, { type: 'PREVIOUS' });
    expect(spotStep.data.label).toEqual('장소 입력');

    spotStep = reducer(spotStep, { type: 'PREVIOUS' });
    expect(spotStep.data.label).toEqual('장소 입력');
  });

  test('course', () => {
    expect(courseStep.data.label).toEqual('기록 확정');

    courseStep = reducer(courseStep, { type: 'PREVIOUS' });
    expect(courseStep.data.label).toEqual('리뷰 입력');

    courseStep = reducer(courseStep, { type: 'PREVIOUS' });
    expect(courseStep.data.label).toEqual('사진 업로드');

    courseStep = reducer(courseStep, { type: 'PREVIOUS' });
    expect(courseStep.data.label).toEqual('장소 입력');

    courseStep = reducer(courseStep, { type: 'PREVIOUS' });
    expect(courseStep.data.label).toEqual('방식 선택');

    courseStep = reducer(courseStep, { type: 'PREVIOUS' });
    expect(courseStep.data.label).toEqual('방식 선택');
  });
});
