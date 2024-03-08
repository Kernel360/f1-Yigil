'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import BaseSearchHistory from './BaseSearchHistory';
import TravelSearchResult from './TravelSearchResult';
import LoadingIndicator from '../LoadingIndicator';

export default function BaseSearchContent() {
  const { state } = useContext(SearchContext);
  const { keyword, result } = state;

  if (result.status === 'start') {
    if (keyword.length === 0) {
      return <BaseSearchHistory />;
    }

    if (state.loading) {
      return (
        <section className="grow flex flex-col justify-center items-center gap-8">
          <LoadingIndicator loadingText="검색 중..." />
        </section>
      );
    }

    return <>추천 검색어</>;
  }

  if (result.status === 'searchEngine') {
    return <>네이버 장소 검색</>;
  }

  return <TravelSearchResult />;
}
