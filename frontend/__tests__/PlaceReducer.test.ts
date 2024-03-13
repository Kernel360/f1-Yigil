import { describe, expect, test } from 'vitest';

import reducer, { createInitialPlace } from '@/context/travel/place/reducer';

import type { TPlaceAction } from '@/context/travel/place/reducer';
import type { TChoosePlace } from '@/context/travel/schema';

const place1: TChoosePlace = {
  name: '장소1',
  address: '도로명주소1',
  mapImageUrl: '지도 이미지1',
  coords: { lat: 37.3595704, lng: 127.105399 },
};

const place2: TChoosePlace = {
  name: '장소2',
  address: '도로명주소2',
  mapImageUrl: '지도 이미지2',
  coords: { lat: 38.3595704, lng: 128.105399 },
};

const place3: TChoosePlace = {
  name: '장소3',
  address: '도로명주소3',
  mapImageUrl: '지도 이미지3',
  coords: { lat: 39.3595704, lng: 129.105399 },
};

describe('Place reducer success test', () => {
  test('With initial place', () => {
    const initial = createInitialPlace(undefined, place1);

    expect(initial.data).toBe(place1);
  });

  test('SET_PLACE', () => {
    const initial = createInitialPlace();

    const nextState = reducer(initial, { type: 'SET_PLACE', payload: place1 });

    expect(nextState.data).toEqual(place1);
  });

  test('ADD_PLACE', () => {
    const initial = createInitialPlace(true);

    const nextState = reducer(initial, {
      type: 'ADD_PLACE',
      payload: place1,
    });

    expect(nextState.type === 'course' && nextState.data[0]).toEqual(place1);
  });

  test('REMOVE_PLACE', () => {
    const initial = createInitialPlace(true);

    if (initial.type === 'course') {
      initial.data.push(place1);

      const nextState = reducer(initial, {
        type: 'REMOVE_PLACE',
        payload: place1,
      });

      expect(nextState.data).toEqual([]);
    }
  });

  test('CHANGE_PLACE_ORDER', () => {
    const places = [place3, place1, place2];

    const initial = createInitialPlace(true);

    if (initial.type === 'course') {
      initial.data.push(place1);
      initial.data.push(place2);
      initial.data.push(place3);

      const nextState = reducer(initial, {
        type: 'CHANGE_PLACE_ORDER',
        payload: places,
      });

      expect(nextState.data).toEqual(places);
    }
  });
});

describe('Place reducer fail test', () => {
  const actions: TPlaceAction[] = [
    { type: 'SET_PLACE', payload: {} },
    { type: 'ADD_PLACE', payload: {} },
    { type: 'REMOVE_PLACE', payload: {} },
    { type: 'CHANGE_PLACE_ORDER', payload: {} },
  ];

  test.each(actions)('$type', (action) => {
    const initial = createInitialPlace();

    const nextState = reducer(initial, action);

    expect(nextState.data).toEqual(initial.data);
  });
});
