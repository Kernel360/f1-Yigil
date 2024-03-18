import { TPlaceState, placeStateSchema } from '../travel/place/reducer';
import { choosePlaceSchema, defaultChoosePlace } from '../travel/schema';

import type { TChoosePlace } from '../travel/schema';

export const defaultAddTravelMapState: TAddTravelMapState = {
  isMapOpen: false,
  isSearchResultOpen: false,
  current: defaultChoosePlace,
  selectedPlace: defaultChoosePlace,
};

export interface TAddTravelMapState {
  isMapOpen: boolean;
  isSearchResultOpen: boolean;
  current: TChoosePlace;
  selectedPlace: TChoosePlace;
}

export type TAddTravelMapAction = {
  type:
    | 'OPEN_MAP'
    | 'CLOSE_MAP'
    | 'OPEN_RESULT'
    | 'CLOSE_RESULT'
    | 'SELECT_PLACE'
    | 'UNSELECT_PLACE'
    | 'SET_CURRENT_PLACE'
    | 'RESET_CURRENT_PLACE';
  payload?: unknown;
};

export default function reducer(
  state: TAddTravelMapState,
  action: TAddTravelMapAction,
): TAddTravelMapState {
  switch (action.type) {
    case 'OPEN_MAP': {
      return { ...state, isMapOpen: true };
    }
    case 'CLOSE_MAP': {
      return { ...state, isMapOpen: false };
    }
    case 'OPEN_RESULT': {
      return { ...state, isSearchResultOpen: true };
    }
    case 'CLOSE_RESULT': {
      return { ...state, isSearchResultOpen: false };
    }
    case 'SELECT_PLACE': {
      const result = choosePlaceSchema.safeParse(action.payload);

      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, selectedPlace: result.data };
    }
    case 'UNSELECT_PLACE': {
      return {
        ...state,
        selectedPlace: defaultChoosePlace,
      };
    }
    case 'SET_CURRENT_PLACE': {
      const result = choosePlaceSchema.safeParse(action.payload);

      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, current: result.data };
    }
    case 'RESET_CURRENT_PLACE': {
      return { ...state, current: defaultChoosePlace };
    }
  }
}
