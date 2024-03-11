import {
  choosePlaceSchema,
  manyInputImageSchema,
  reviewSchema,
} from '../schema';

import type { TSpotState } from '../schema';

export interface TSpotAction {
  type: 'SET_PLACE' | 'SET_IMAGES' | 'SET_REVIEW';
  payload?: unknown;
}

export const initialSpotState: TSpotState = {
  place: { name: '', address: '', mapImageUrl: '', coords: { lng: 0, lat: 0 } },
  images: [],
  review: { rate: 1, content: '' },
};

export default function reducer(
  state: TSpotState,
  action: TSpotAction,
): TSpotState {
  switch (action.type) {
    case 'SET_PLACE': {
      const result = choosePlaceSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, place: result.data };
    }
    case 'SET_IMAGES': {
      const result = manyInputImageSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, images: result.data };
    }
    case 'SET_REVIEW': {
      const result = reviewSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, review: result.data };
    }
  }
}
