import { z } from 'zod';

import { inputImageSchema } from '@/app/_components/images';

import type { TInputImage } from '@/app/_components/images';

const choosePlaceSchema = z.object({
  name: z.string(),
  address: z.string(),
  mapImageUrl: z.string(),
  coords: z.object({ lng: z.number(), lat: z.number() }),
});

export type TChoosePlace = z.infer<typeof choosePlaceSchema>;

const reviewSchema = z.object({
  rate: z.number().int().gte(1).lte(5),
  content: z.string().max(30),
});

export type TSpotReview = z.infer<typeof reviewSchema>;

export interface TSpotState {
  place: TChoosePlace;
  images: TInputImage[];
  review: TSpotReview;
}

export interface TSpotAction {
  type: 'SET_PLACE' | 'SET_IMAGES' | 'SET_REVIEW';
  payload?: unknown;
}

export const initialSpotState: TSpotState = {
  place: { name: '', address: '', mapImageUrl: '', coords: { lng: 0, lat: 0 } },
  images: [],
  review: { rate: 1, content: '' },
};

export function reducer(state: TSpotState, action: TSpotAction): TSpotState {
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
      const result = z.array(inputImageSchema).safeParse(action.payload);

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
