import { isEqualPlace } from '../utils';
import { choosePlaceSchema, manyChoosePlaceSchema } from '../schema';

import type { TChoosePlace } from '../schema';

export type TPlaceState =
  | { type: 'spot'; data: TChoosePlace }
  | { type: 'course'; data: TChoosePlace[] };

export const defaultPlace: TPlaceState = {
  type: 'spot',
  data: {
    name: '',
    address: '',
    mapImageUrl: '',
    coords: { lng: 0, lat: 0 },
  },
};

export function createInitialPlace(
  course?: boolean,
  place?: TChoosePlace,
): TPlaceState {
  if (course) {
    return { type: 'course', data: [] };
  }

  if (place) {
    return { type: 'spot', data: place };
  }

  return defaultPlace;
}

export interface TPlaceAction {
  type: 'SET_PLACE' | 'CHANGE_PLACE_ORDER' | 'ADD_PLACE' | 'REMOVE_PLACE';
  payload?: unknown;
}

export default function reducer(
  state: TPlaceState,
  action: TPlaceAction,
): TPlaceState {
  switch (action.type) {
    case 'SET_PLACE': {
      if (state.type === 'course') {
        return state;
      }

      const result = choosePlaceSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return state;
      }

      return { ...state, data: result.data };
    }
    case 'CHANGE_PLACE_ORDER': {
      if (state.type === 'spot') {
        return state;
      }

      const result = manyChoosePlaceSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return state;
      }

      return { ...state, data: result.data };
    }
    case 'ADD_PLACE': {
      if (state.type === 'spot') {
        return state;
      }

      const result = choosePlaceSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return state;
      }

      return { ...state, data: [...state.data, result.data] };
    }
    case 'REMOVE_PLACE': {
      if (state.type === 'spot') {
        return state;
      }

      const result = choosePlaceSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return state;
      }

      const nextData = state.data.filter(
        (place) => !isEqualPlace(place, result.data),
      );

      return { ...state, data: nextData };
    }
  }
}
