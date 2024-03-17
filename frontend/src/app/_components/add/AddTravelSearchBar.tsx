'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { defaultChoosePlace } from '@/context/travel/schema';

import BaseSearchBar from '../search/BaseSearchBar';
import { searchNaverAction } from '../search/action';

import type { EventFor } from '@/types/type';

export default function AddTravelSearchBar() {
  const [, dispatchTravelMap] = useContext(AddTravelMapContext);
  const [, dispatch] = useContext(SearchContext);

  function onFocus(event: EventFor<'input', 'onFocus'>) {
    dispatchTravelMap({
      type: 'SET_CURRENT_PLACE',
      payload: defaultChoosePlace,
    });
    dispatchTravelMap({ type: 'UNSELECT_PLACE' });
    dispatchTravelMap({ type: 'OPEN_MAP' });
    dispatchTravelMap({ type: 'OPEN_RESULT' });
  }

  async function onSearch(term: string) {
    const result = await searchNaverAction(term);

    if (result.status === 'failed') {
      console.error(result.message);
      dispatch({ type: 'SET_ERROR', payload: result.message });
      return;
    }

    dispatch({ type: 'SEARCH_NAVER', payload: result.data });
  }

  function onCancel() {
    dispatch({ type: 'INIT_RESULT' });
    dispatch({ type: 'EMPTY_KEYWORD' });
  }

  return (
    <BaseSearchBar
      placeholder="장소를 입력하세요."
      onCancel={onCancel}
      onFocus={onFocus}
      onSearch={onSearch}
    />
  );
}
