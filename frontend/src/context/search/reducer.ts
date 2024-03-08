import { z } from 'zod';

import { fetchableSchema, placeSchema } from '@/types/response';

import type { TPlace } from '@/types/response';

const keywordSchema = z.string();
const historyIndexSchema = z.number().int();
const searchPlaceData = fetchableSchema(placeSchema);

export type TSearchState = {
  histories: string[];
  keyword: string;
  showHistory: boolean;
  result:
    | { from: 'start' }
    | { from: 'none' }
    | { from: 'searchEngine'; content: string[] }
    | {
        from: 'backend';
        data:
          | {
              type: 'place';
              hasNext: boolean;
              content: TPlace[];
            }
          | { type: 'course'; hasNext: boolean };
      };
};

export const defaultSearchState: TSearchState = {
  showHistory: false,
  keyword: '',
  histories: [],
  result: { from: 'none' },
};

export type TSearchAction = {
  type:
    | 'SET_KEYWORD'
    | 'ADD_HISTORY'
    | 'DELETE_HISTORY'
    | 'CLEAR_HISTORY'
    | 'SEARCH_PLACE'
    | 'SEARCH_COURSE'
    | 'SEARCH_NAVER';
  payload?: any;
};

export function createInitialState(
  histories: string[],
  showHistory: boolean = false,
  initialKeyword: string = '',
): TSearchState {
  return {
    histories,
    showHistory: showHistory,
    keyword: initialKeyword,
    result: { from: 'start' },
  };
}

export function searchReducer(
  state: TSearchState,
  action: TSearchAction,
): TSearchState {
  const { histories, keyword } = state;

  switch (action?.type) {
    case 'SET_KEYWORD': {
      const result = keywordSchema.safeParse(action.payload);

      if (result.success) {
        return { ...state, keyword: result.data };
      }

      return { ...state };
    }

    case 'ADD_HISTORY': {
      if (histories.includes(keyword)) {
        return { ...state };
      }

      return { ...state, histories: [...histories, keyword] };
    }

    case 'DELETE_HISTORY': {
      const result = historyIndexSchema.safeParse(action.payload);

      if (result.success) {
        const nextHistories = histories.filter(
          (_, index) => index !== result.data,
        );

        return { ...state, histories: nextHistories };
      }

      return { ...state };
    }

    case 'CLEAR_HISTORY': {
      return { ...state, histories: [] };
    }

    case 'SEARCH_PLACE': {
      // const result = searchPlaceData.safeParse(action.payload);

      // if (result.success) {
      //   const { content, has_next } = result.data;

      //   return {
      //     ...state,
      //     result: {
      //       from: 'backend',
      //       data: { type: 'place', hasNext: has_next, content },
      //     },
      //   };
      // }

      return {
        ...state,
        result: {
          from: 'backend',
          data: { type: 'place', hasNext: false, content: [] },
        },
      };
    }

    case 'SEARCH_COURSE': {
      return {
        ...state,
        result: {
          from: 'backend',
          data: { type: 'course', hasNext: false },
        },
      };
    }

    case 'SEARCH_NAVER': {
      return { ...state };
    }
  }
}
