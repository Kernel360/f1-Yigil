'use client';

import type { TImageData } from '../../images/ImageHandler';

type TRating = 1 | 2 | 3 | 4 | 5;

interface TAddSpotProps {
  name: string;
  address: string;
  spotMapImageUrl: string;
  images: TImageData[];
  coords: [number, number];
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

const examplePlaceName = '이구성수';
const exampleAddressName = '서울 성동구 아차산로 78';
const placeholderImageUrl = 'https://placehold.co/400x300';

export const initialAddSpotState: TAddSpotProps = {
  name: examplePlaceName,
  address: exampleAddressName,
  spotMapImageUrl: placeholderImageUrl,
  images: [],
  coords: [0, 0],
  rating: 1,
  review: { review: '' },
};

export const AddSpotContext = createContext<TAddSpotProps>(initialAddSpotState);

export function addSpotReducer(
  state: TAddSpotProps,
  action: TAddSpotAction,
): TAddSpotProps {
  console.log(`Dispatch from ${action.type}`);

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
      const coords = action.payload as [number, number];
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
