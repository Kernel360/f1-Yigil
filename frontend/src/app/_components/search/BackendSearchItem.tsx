'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import BaseSearchItem from '../search/BaseSearchItem';
import { searchPlaces } from './action';

export default function BackendSearchItem({
  name,
  erasable,
}: {
  name: string;
  erasable?: boolean;
}) {
  const [search, dispatch] = useContext(SearchContext);

  async function onSelect() {
    dispatch({ type: 'SET_LOADING', payload: true });

    try {
      if (search.backendSearchType === 'place') {
        const result = await searchPlaces(name);

        dispatch({ type: 'SEARCH_PLACE', payload: result });
      }
    } catch (error) {
      console.error(error);
    } finally {
      dispatch({ type: 'SET_LOADING', payload: false });
    }
  }

  return (
    <BaseSearchItem label={name} onSelect={onSelect} erasable={erasable} />
  );
}
