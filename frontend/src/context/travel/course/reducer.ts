import {
  choosePlaceSchema,
  currentSpotImagesSchema,
  currentSpotPlaceSchema,
  currentSpotReviewSchema,
  imageUrlSchema,
  lineStringSchema,
  manySpotStateSchema,
  reviewSchema,
  spotStateSchema,
} from '../schema';
import { isEqualSpot } from '../utils';

import type { TCourseState, TSpotState } from '../schema';
import { TExistingSpot, existingSpotsSchema } from '@/types/response';

export const initialCourseState: TCourseState = {
  review: { title: '', content: '', rate: 1 },
  spots: [],
  staticMapImageUrl: '',
  lineString: { type: 'LineString', coordinates: [] },
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
    | 'SET_EXISTING_SPOTS'
    | 'INIT_COURSE'
    | 'SET_COURSE_STATIC_MAP'
    | 'SET_PATH';
  payload?: unknown;
}

function convertExistingSpot(existing: TExistingSpot): TSpotState {
  const {
    place_name,
    place_address,
    point,
    image_urls,
    rate,
    description,
    spot_id,
    create_date,
  } = existing;

  const latlng = { lat: point.y, lng: point.x };

  return {
    id: spot_id,
    createDate: create_date,
    place: {
      name: place_name,
      address: place_address,
      coords: latlng,
      mapImageUrl: '',
    },
    images: { type: 'exist', data: image_urls },
    review: {
      rate,
      content: description,
    },
  };
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
      const result = choosePlaceSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      const newSpot: TSpotState = {
        place: result.data,
        images: { type: 'new', data: [] },
        review: {
          rate: 1,
          content: '',
        },
      };

      return { ...state, spots: [...state.spots, newSpot] };
    }
    case 'SET_EXISTING_SPOTS': {
      const result = existingSpotsSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return state;
      }

      const newSpots: TSpotState[] = result.data.spot_details.map((existing) =>
        convertExistingSpot(existing),
      );

      return { ...state, spots: newSpots };
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
    case 'SET_COURSE_STATIC_MAP': {
      const result = imageUrlSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, staticMapImageUrl: result.data };
    }
    case 'SET_PATH': {
      const result = lineStringSchema.safeParse(action.payload);

      /**
       * @todo SET_ERROR for Toast
       */
      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, lineString: result.data };
    }
  }
}
