'use client';

import { createContext, useEffect, useReducer, useState } from 'react';
import {
  createInitialState,
  defaultSearchState,
  searchReducer,
} from './reducer';

import type { Dispatch, ReactNode } from 'react';
import type { TSearchAction, TSearchState } from './reducer';
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
  loading,
  children,
}: {
  loading?: boolean;
  showHistory?: boolean;
  initialKeyword?: string;
  children: ReactNode;
}) {
  const histories = getSearchHistory(searchKey);

  const initialState = createInitialState(
    histories,
    initialKeyword,
    showHistory,
    loading,
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
