'use client';

import { useContext } from 'react';
import { searchCourses, searchPlaces } from '../search/action';

import BaseSearchBar from '../search/BaseSearchBar';
import { SearchContext } from '@/context/search/SearchContext';

export default function BackendSearchBar() {
  const [search, dispatch] = useContext(SearchContext);

  async function onSearch(term: string) {
    if (search.backendSearchType === 'place') {
      const result = await searchPlaces(term, 1, 5, search.sortOptions);

      if (result.status === 'failed') {
        dispatch({ type: 'SET_ERROR', payload: result.message });
        return;
      }

      dispatch({ type: 'SEARCH_PLACE', payload: result.data });

      return;
    }

    const result = await searchCourses(term);

    if (result.status === 'failed') {
      dispatch({ type: 'SET_ERROR', payload: result.message });
      return;
    }

    dispatch({ type: 'SEARCH_COURSE', payload: result.data });
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
