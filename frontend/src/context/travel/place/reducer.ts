import z from 'zod';

import { isEqualArray, isEqualPlace } from '../utils';
import {
  choosePlaceSchema,
  defaultChoosePlace,
  manyChoosePlaceSchema,
} from '../schema';

import type { TChoosePlace } from '../schema';

export const placeStateSchema = z.discriminatedUnion('type', [
  z.object({ type: z.literal('spot'), data: choosePlaceSchema }),
  z.object({ type: z.literal('course'), data: z.array(choosePlaceSchema) }),
]);

export type TPlaceState = z.infer<typeof placeStateSchema>;

export const defaultPlaceState: TPlaceState = {
  type: 'spot',
  data: defaultChoosePlace,
};

export function isDefaultChoosePlace(choosePlace: TChoosePlace) {
  return isEqualPlace(choosePlace, defaultChoosePlace);
}

/**
 * @impure
 */
export function isDefaultPlace(state: TPlaceState) {
  if (state.type === 'spot' && defaultPlaceState.type === 'spot') {
    return isEqualPlace(state.data, defaultPlaceState.data);
  }

  if (state.type === 'course') {
    return state.data.length === 0;
  }

  return false;
}

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

  return defaultPlaceState;
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

      const result = placeStateSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return state;
      }

      return result.data;
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
