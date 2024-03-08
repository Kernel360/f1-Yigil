'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import BaseSearchHistory from './BaseSearchHistory';
import TravelSearchResult from './TravelSearchResult';

export default function BaseSearchContent() {
  const { state } = useContext(SearchContext);
  const { keyword, result } = state;

  if (result.from === 'start') {
    if (keyword.length === 0) {
      return <BaseSearchHistory />;
    } else {
      return <>추천 검색어</>;
    }
  }

  if (result.from === 'none') {
    return <>결과 없음</>;
  }

  if (result.from === 'searchEngine') {
    return <>네이버 장소 검색</>;
  }

  return <TravelSearchResult />;
}
