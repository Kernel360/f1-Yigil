import { z } from 'zod';

const currentPlaceSchema = z.object({
  name: z.string(),
  roadAddress: z.string(),
  coords: z.object({
    lng: z.number(),
    lat: z.number(),
  }),
});

type TCurrentPlace = z.infer<typeof currentPlaceSchema>;

const selectedPlaceSchema = z.discriminatedUnion('type', [
  z.object({ type: z.literal('spot'), place: currentPlaceSchema }),
  z.object({
    type: z.literal('course'),
    places: z.array(currentPlaceSchema),
  }),
]);

const defaultCurrentPlace: TCurrentPlace = {
  name: '',
  roadAddress: '',
  coords: { lng: 0, lat: 0 },
};

export const defaultAddTravelMapState: TAddTravelMapState = {
  isMapOpen: false,
  isSearchResultOpen: false,
  current: { type: 'spot', place: defaultCurrentPlace },
};

export interface TAddTravelMapState {
  isMapOpen: boolean;
  isSearchResultOpen: boolean;
  current:
    | { type: 'spot'; place: TCurrentPlace }
    | { type: 'course'; places: TCurrentPlace[] };
}

export type TAddTravelMapAction = {
  type:
    | 'OPEN_MAP'
    | 'CLOSE_MAP'
    | 'OPEN_RESULT'
    | 'CLOSE_RESULT'
    | 'SET_CURRENT_PLACE';
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
    case 'SET_CURRENT_PLACE': {
      const result = selectedPlaceSchema.safeParse(action.payload);

      console.log(action.payload);

      if (!result.success) {
        console.error(result.error.message);
        return { ...state };
      }

      return { ...state, current: result.data };
    }
  }
}
