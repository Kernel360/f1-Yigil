'use client';

import { createContext, useEffect, useReducer } from 'react';
import {
  createInitialState,
  defaultSearchState,
  searchReducer,
} from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TSearchState, TSearchAction } from './reducer';
import { parseSearchHistory } from '@/utils';

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
  children,
}: {
  showHistory?: boolean;
  initialKeyword?: string;
  children: ReactNode;
}) {
  const histories = getSearchHistory(searchKey);

  const initialState = createInitialState(
    histories,
    initialKeyword,
    showHistory,
  );

  const [state, dispatch] = useReducer(searchReducer, initialState);

  useEffect(() => {
    localStorage.setItem(searchKey, JSON.stringify(state.histories));
  }, [state.histories]);

  return (
    <SearchContext.Provider value={[state, dispatch]}>
      {children}
    </SearchContext.Provider>
  );
}
