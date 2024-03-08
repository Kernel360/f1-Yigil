'use client';

import type { TImageData } from '../../images/ImageHandler';

type TRating = 1 | 2 | 3 | 4 | 5;

export interface TAddSpotProps {
  name: string;
  address: string;
  spotMapImageUrl: string;
  images: TImageData[];
  coords: { lat: number; lng: number };
  rating: TRating;
  review: { title?: string; review: string };
}

export interface TAddSpotAction {
  type:
    | 'SET_NAME'
    | 'SET_ADDRESS'
    | 'SET_SPOT_MAP_URL'
    | 'SET_IMAGES'
    | 'SET_COORDS'
    | 'SET_RATING'
    | 'SET_REVIEW';

  payload: any;
}

import { createContext } from 'react';

export const initialAddSpotState: TAddSpotProps = {
  name: '',
  address: '',
  spotMapImageUrl: '',
  images: [],
  coords: { lat: 0, lng: 0 },
  rating: 1,
  review: { review: '' },
};

export const AddSpotContext = createContext<TAddSpotProps>(initialAddSpotState);

export function addSpotReducer(
  state: TAddSpotProps,
  action: TAddSpotAction,
): TAddSpotProps {
  switch (action.type) {
    case 'SET_NAME': {
      const name = action.payload as string;
      return { ...state, name };
    }
    case 'SET_ADDRESS': {
      const address = action.payload as string;
      return { ...state, address };
    }
    case 'SET_SPOT_MAP_URL': {
      const spotMapImageUrl = action.payload as string;
      return { ...state, spotMapImageUrl };
    }
    case 'SET_IMAGES': {
      const images = action.payload as TImageData[];

      return { ...state, images };
    }
    case 'SET_COORDS': {
      const coords = action.payload as { lat: number; lng: number };
      return { ...state, coords };
    }
    case 'SET_RATING': {
      const rating = action.payload as TRating;
      return { ...state, rating };
    }
    case 'SET_REVIEW': {
      const review = action.payload as { title?: string; review: string };

      // 30자 이상 계속 입력해도 리렌더링 발생
      // 최적화 필요

      return { ...state, review };
    }
  }
}
