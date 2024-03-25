import { z } from 'zod';

export interface TAddTravelExistsState {
  selectedSpotsId: number[];
}

export const defaultAddTravelExistsState: TAddTravelExistsState = {
  selectedSpotsId: [],
};

export interface TAddTravelExistsAction {
  type: 'SELECT_SPOT' | 'UNSELECT_SPOT' | 'INIT';
  payload?: unknown;
}

export default function reducer(
  state: TAddTravelExistsState,
  action: TAddTravelExistsAction,
): TAddTravelExistsState {
  switch (action.type) {
    case 'SELECT_SPOT': {
      const result = z.number().safeParse(action.payload);

      if (!result.success) {
        console.error(result.error.issues);
        return state;
      }

      return {
        ...state,
        selectedSpotsId: [...state.selectedSpotsId, result.data],
      };
    }
    case 'UNSELECT_SPOT': {
      const result = z.number().safeParse(action.payload);

      if (!result.success) {
        console.error(result.error.issues);
        return state;
      }

      const nextIds = state.selectedSpotsId.filter((id) => id !== result.data);

      return { ...state, selectedSpotsId: nextIds };
    }

    case 'INIT': {
      return { ...state, selectedSpotsId: [] };
    }
  }

  return state;
}
