'use client';

import { useContext } from 'react';
import { searchPlaces } from '../search/action';

import BaseSearchBar from '../search/BaseSearchBar';
import { SearchContext } from '@/context/search/SearchContext';

export default function BackendSearchBar() {
  const [search, dispatch] = useContext(SearchContext);

  async function onSearch(term: string) {
    if (search.backendSearchType === 'place') {
      const result = await searchPlaces(term);

      dispatch({ type: 'SEARCH_PLACE', payload: result });

      return;
    }

    // const result = await searchCourses(term);
  }

  function onCancel() {
    dispatch({ type: 'EMPTY_KEYWORD' });
  }

  return (
    <BaseSearchBar
      key="backend"
      placeholder="검색어를 입력하세요."
      onSearch={onSearch}
      onCancel={onCancel}
    />
  );
}
