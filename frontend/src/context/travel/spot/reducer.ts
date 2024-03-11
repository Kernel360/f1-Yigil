import { z } from 'zod';

import type { TImageData } from '@/app/_components/images/ImageHandler';

const choosePlaceSchema = z.object({
  name: z.string(),
  address: z.string(),
  mapImageUrl: z.string(),
  coords: z.object({ lng: z.number(), lat: z.number() }),
});

type TChoosePlace = z.infer<typeof choosePlaceSchema>;

export interface TSpotState {
  place: TChoosePlace;
  images: TImageData[];
  rating: 1 | 2 | 3 | 4 | 5;
  review: { title?: string; content: string };
}

export interface TSpotAction {
  type: 'SET_PLACE' | 'SET_IMAGES' | 'SET_REVIEW';
  payload?: unknown;
}

export const initialSpotState: TSpotState = {
  place: { name: '', address: '', mapImageUrl: '', coords: { lng: 0, lat: 0 } },
  images: [],
  rating: 1,
  review: { content: '' },
};

export function reducer(state: TSpotState, action: TSpotAction): TSpotState {
  switch (action.type) {
    case 'SET_PLACE': {
      return { ...state };
    }
    case 'SET_IMAGES': {
      return { ...state };
    }
    case 'SET_REVIEW': {
      return { ...state };
    }
  }
}
