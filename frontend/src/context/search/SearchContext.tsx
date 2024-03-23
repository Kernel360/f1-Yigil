'use client';

import { createContext, useEffect, useReducer } from 'react';
import reducer, { createInitialState, defaultSearchState } from './reducer';

import { parseSearchHistory } from '@/utils';

import type { Dispatch, ReactNode } from 'react';
import type { TSearchState, TSearchAction } from './reducer';

export const SearchContext = createContext<
  [TSearchState, Dispatch<TSearchAction>]
>([defaultSearchState, () => {}]);

const searchKey = 'search-history';

function getSearchHistory(searchKey: string) {
  return typeof window === 'undefined'
    ? []
    : parseSearchHistory(localStorage.getItem(searchKey));
}

export default function SearchProvider({
  showHistory,
  initialKeyword,
  backendSearchType,
  children,
}: {
  showHistory?: boolean;
  initialKeyword?: string;
  backendSearchType?: 'place' | 'course';
  children: ReactNode;
}) {
  const histories = getSearchHistory(searchKey);

  const initialState = createInitialState(
    histories,
    initialKeyword,
    showHistory,
    backendSearchType,
  );

  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    localStorage.setItem(searchKey, JSON.stringify(state.histories));
  }, [state.histories]);

  return (
    <SearchContext.Provider value={[state, dispatch]}>
      {children}
    </SearchContext.Provider>
  );
}
