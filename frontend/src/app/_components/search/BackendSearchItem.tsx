'use client';

import { useContext } from 'react';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';
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
  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { replace } = useRouter();

  const [search, dispatch] = useContext(SearchContext);

  function handleChange(term: string) {
    const params = new URLSearchParams(searchParams);

    params.set('keyword', term);
    replace(`${pathname}?${params.toString()}`);
    dispatch({ type: 'SET_KEYWORD', payload: term });
  }

  async function onSelect() {
    handleChange(name);
    dispatch({ type: 'SET_LOADING', payload: true });
    dispatch({ type: 'SET_CURRENT_TERM' });

    try {
      if (search.backendSearchType === 'place') {
        const result = await searchPlaces(name);

        if (result.status === 'failed') {
          dispatch({ type: 'SET_ERROR', payload: result.message });
          return;
        }

        dispatch({ type: 'SEARCH_PLACE', payload: result.data });
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
