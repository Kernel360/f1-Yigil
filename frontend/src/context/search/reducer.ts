import { z } from 'zod';

import { placeSchema } from '@/types/response';

import type { TPlace } from '@/types/response';

const keywordSchema = z.string();
const searchHistorySchema = z.string();
const loadingSchema = z.boolean();

const pageSchema = z.number().int();

// 응답의 진짜 내용을 담는 이름이 굳이 places, courses... 이어야 할까?
// 응답 프로퍼티명이 달라서 한큐에 처리하기 힘듦
// const searchPlaceData = fetchableSchema(placeSchema);
export const searchPlaceData = z.object({
  places: z.array(placeSchema),
  has_next: z.boolean(),
});

export type TSearchState = {
  loading: boolean;
  showHistory: boolean;
  keyword: string;
  histories: string[];
  result:
    | { status: 'start' }
    | { status: 'searchEngine'; content: string[] }
    | { status: 'error'; message: string[] }
    | {
        status: 'backend';
        data:
          | {
              type: 'place';
              hasNext: boolean;
              currentPage: number;
              content: TPlace[];
            }
          | { type: 'course'; hasNext: boolean; currentPage: number };
      };
};

export const defaultSearchState: TSearchState = {
  loading: false,
  showHistory: false,
  keyword: '',
  histories: [],
  result: { status: 'start' },
};

export type TSearchAction = {
  type:
    | 'SET_KEYWORD'
    | 'EMPTY_KEYWORD'
    | 'ADD_HISTORY'
    | 'DELETE_HISTORY'
    | 'CLEAR_HISTORY'
    | 'SET_LOADING'
    | 'SEARCH_PLACE'
    | 'MORE_PLACE'
    | 'SEARCH_COURSE'
    | 'SEARCH_NAVER';
  payload?: any;
};

export function createInitialState(
  histories: string[],
  initialKeyword: string = '',
  showHistory: boolean = false,
): TSearchState {
  return {
    histories,
    loading: false,
    showHistory: showHistory,
    keyword: initialKeyword,
    result: { status: 'start' },
  };
}

export function searchReducer(
  state: TSearchState,
  action: TSearchAction,
): TSearchState {
  const { histories, keyword, result } = state;

  switch (action.type) {
    case 'SET_KEYWORD': {
      const result = keywordSchema.safeParse(action.payload);

      if (result.success) {
        return { ...state, keyword: result.data };
      }

      return { ...state };
    }

    case 'EMPTY_KEYWORD': {
      return { ...state, keyword: '', result: { status: 'start' } };
    }

    case 'ADD_HISTORY': {
      if (histories.includes(keyword)) {
        return { ...state };
      }

      return { ...state, histories: [keyword, ...histories] };
    }

    case 'DELETE_HISTORY': {
      const result = searchHistorySchema.safeParse(action.payload);

      if (result.success) {
        const nextHistories = histories.filter(
          (history) => result.data !== history,
        );

        return { ...state, histories: nextHistories };
      }

      return { ...state };
    }

    case 'CLEAR_HISTORY': {
      return { ...state, histories: [] };
    }

    case 'SET_LOADING': {
      const result = loadingSchema.safeParse(action.payload);

      if (!result.success) {
        return { ...state };
      }

      return { ...state, loading: result.data };
    }

    case 'SEARCH_PLACE': {
      const json = action.payload;

      const searchPlaceResult = searchPlaceData.safeParse(json);

      if (!searchPlaceResult.success) {
        const errors = searchPlaceResult.error.errors.map(
          (err) => `${err.code}: ${err.message}`,
        );
        return { ...state, result: { status: 'error', message: errors } };
      }

      const { places, has_next } = searchPlaceResult.data;

      return {
        ...state,
        result: {
          status: 'backend',
          data: {
            type: 'place',
            currentPage: 1,
            content: places,
            hasNext: has_next,
          },
        },
      };
    }

    case 'MORE_PLACE': {
      const json = action.payload.json;
      const page = action.payload.nextPage;

      const searchPlaceResult = searchPlaceData.safeParse(json);
      const nextPageResult = pageSchema.safeParse(page);

      if (searchPlaceResult.success && nextPageResult.success) {
        const { places, has_next } = searchPlaceResult.data;

        if (
          state.result.status === 'backend' &&
          state.result.data.type === 'place'
        ) {
          return {
            ...state,
            result: {
              status: 'backend',
              data: {
                type: 'place',
                currentPage: nextPageResult.data,
                content: [...state.result.data.content, ...places],
                hasNext: has_next,
              },
            },
          };
        }
      }

      return { ...state };
    }

    case 'SEARCH_COURSE': {
      return {
        ...state,
        result: {
          status: 'backend',
          data: { type: 'course', hasNext: false, currentPage: 1 },
        },
      };
    }

    case 'SEARCH_NAVER': {
      return { ...state };
    }
  }
}
