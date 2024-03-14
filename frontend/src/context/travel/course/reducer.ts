import {
  currentSpotImagesSchema,
  currentSpotPlaceSchema,
  currentSpotReviewSchema,
  manySpotStateSchema,
  reviewSchema,
  spotStateSchema,
} from '../schema';
import { isEqualSpot } from '../utils';

import type { TCourseState } from '../schema';

export const initialCourseState: TCourseState = {
  review: { title: '', content: '', rate: 1 },
  spots: [],
};

export interface TCourseAction {
  type:
    | 'CHANGE_SPOT_ORDER'
    | 'ADD_SPOT'
    | 'REMOVE_SPOT'
    | 'SET_SPOT_PLACE'
    | 'SET_SPOT_IMAGES'
    | 'SET_SPOT_REVIEW'
    | 'SET_COURSE_REVIEW'
    | 'INIT_COURSE';
  payload?: unknown;
}

export default function reducer(
  state: TCourseState,
  action: TCourseAction,
): TCourseState {
  switch (action.type) {
    case 'CHANGE_SPOT_ORDER': {
      const result = manySpotStateSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, spots: result.data };
    }
    case 'ADD_SPOT': {
      const result = spotStateSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, spots: [...state.spots, result.data] };
    }
    case 'REMOVE_SPOT': {
      const result = spotStateSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      const nextSpots = state.spots.filter(
        (spot) => !isEqualSpot(spot, result.data),
      );

      return { ...state, spots: nextSpots };
    }
    case 'SET_SPOT_PLACE': {
      const result = currentSpotPlaceSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      const { data, index } = result.data;

      const nextSpots = [...state.spots];
      nextSpots[index].place = data;

      return { ...state, spots: nextSpots };
    }
    case 'SET_SPOT_IMAGES': {
      const result = currentSpotImagesSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      const { data, index } = result.data;

      const nextSpots = [...state.spots];
      nextSpots[index].images = data;

      return { ...state, spots: nextSpots };
    }
    case 'SET_SPOT_REVIEW': {
      const result = currentSpotReviewSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      const { data, index } = result.data;

      const nextSpots = [...state.spots];
      nextSpots[index].review = data;

      return { ...state, spots: nextSpots };
    }
    case 'SET_COURSE_REVIEW': {
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
    case 'INIT_COURSE': {
      return initialCourseState;
    }
  }
}
