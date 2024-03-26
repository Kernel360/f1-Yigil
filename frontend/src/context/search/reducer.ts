import { z } from 'zod';

import { courseDetailSchema, placeSchema } from '@/types/response';

import type { TCourseDetail, TPlace } from '@/types/response';

const keywordSchema = z.string();
const errorSchema = z.string();
const searchHistorySchema = z.string();
const loadingSchema = z.boolean();
const sortOptionSchema = z.string();

const engineSearchSchema = z.array(
  z.object({
    name: z.string(),
    roadAddress: z.string(),
  }),
);

// 응답의 진짜 내용을 담는 이름이 굳이 places, courses... 이어야 할까?
// 응답 프로퍼티명이 달라서 한큐에 처리하기 힘듦
// const searchPlaceSchema = fetchableSchema(placeSchema);
export const searchPlaceSchema = z.object({
  places: z.array(placeSchema),
  has_next: z.boolean(),
});

export const searchCourseSchema = z.object({
  courses: z.array(courseDetailSchema),
  has_next: z.boolean(),
});

export type TSearchState = {
  loading: boolean;
  showHistory: boolean;
  keyword: string;
  currentTerm: string;
  histories: string[];

  sortOptions: string;

  backendSearchType: 'place' | 'course';

  results:
    | { status: 'start' }
    | { status: 'failed'; message: string }
    | {
        status: 'success';
        content:
          | {
              from: 'searchEngine';
              places: { name: string; roadAddress: string }[];
            }
          | {
              from: 'backend';
              data: {
                type: 'place';
                hasNext: boolean;
                places: TPlace[];
              };
            }
          | {
              from: 'backend';
              data: {
                type: 'course';
                hasNext: boolean;
                courses: TCourseDetail[];
              };
            };
      };
};

export const defaultSearchState: TSearchState = {
  loading: false,
  showHistory: false,
  keyword: '',
  currentTerm: '',
  histories: [],
  sortOptions: 'desc',
  backendSearchType: 'place',
  results: { status: 'start' },
};

export type TSearchAction = {
  type:
    | 'SET_KEYWORD'
    | 'SET_CURRENT_TERM'
    | 'EMPTY_KEYWORD'
    | 'ADD_HISTORY'
    | 'DELETE_HISTORY'
    | 'CLEAR_HISTORY'
    | 'SET_SORT_OPTION'
    | 'INIT_RESULT'
    | 'SET_LOADING'
    | 'SET_ERROR'
    | 'SEARCH_PLACE'
    | 'SEARCH_COURSE'
    | 'SEARCH_NAVER';
  payload?: any;
};

export function createInitialState(
  histories: string[],
  initialKeyword: string = '',
  showHistory: boolean = false,
  backendSearchType: 'place' | 'course' = 'place',
): TSearchState {
  return {
    histories,
    loading: false,
    showHistory: showHistory,
    keyword: initialKeyword,
    currentTerm: initialKeyword,
    sortOptions: 'desc',
    backendSearchType,
    results: { status: 'start' },
  };
}

export default function reducer(
  state: TSearchState,
  action: TSearchAction,
): TSearchState {
  const { histories, keyword } = state;

  switch (action.type) {
    case 'SET_KEYWORD': {
      const result = keywordSchema.safeParse(action.payload);

      if (result.success) {
        return { ...state, keyword: result.data };
      }

      return { ...state };
    }

    case 'SET_CURRENT_TERM': {
      return { ...state, currentTerm: keyword };
    }

    case 'EMPTY_KEYWORD': {
      return { ...state, keyword: '', results: { status: 'start' } };
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

    case 'SET_SORT_OPTION': {
      const result = sortOptionSchema.safeParse(action.payload);

      if (!result.success) {
        return { ...state };
      }

      return { ...state, sortOptions: result.data };
    }

    case 'INIT_RESULT': {
      return { ...state, results: { status: 'start' } };
    }

    case 'SET_LOADING': {
      const result = loadingSchema.safeParse(action.payload);

      if (!result.success) {
        return { ...state };
      }

      return { ...state, loading: result.data };
    }

    case 'SET_ERROR': {
      const result = errorSchema.safeParse(action.payload);

      if (!result.success) {
        return { ...state };
      }

      return { ...state, results: { status: 'failed', message: result.data } };
    }

    case 'SEARCH_PLACE': {
      const json = action.payload;

      const result = searchPlaceSchema.safeParse(json);

      if (!result.success) {
        console.log(result.error.message);

        return {
          ...state,
          results: { status: 'failed', message: result.error.message },
        };
      }

      const { places, has_next } = result.data;

      return {
        ...state,
        backendSearchType: 'place',
        results: {
          status: 'success',
          content: {
            from: 'backend',
            data: {
              type: 'place',
              places,
              hasNext: has_next,
            },
          },
        },
      };
    }

    case 'SEARCH_COURSE': {
      const json = action.payload;

      const result = searchCourseSchema.safeParse(json);

      if (!result.success) {
        console.log(result.error.message);

        return {
          ...state,
          results: { status: 'failed', message: result.error.message },
        };
      }

      return {
        ...state,
        backendSearchType: 'course',
        results: {
          status: 'success',
          content: {
            from: 'backend',
            data: {
              type: 'course',
              hasNext: result.data.has_next,
              courses: result.data.courses,
            },
          },
        },
      };
    }

    case 'SEARCH_NAVER': {
      const result = engineSearchSchema.safeParse(action.payload);

      if (!result.success) {
        console.log(result.error.message);
        return { ...state };
      }

      return {
        ...state,
        results: {
          status: 'success',
          content: { from: 'searchEngine', places: result.data },
        },
      };
    }
  }
}
