'use client';

import { useContext, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import Select from '../ui/select/Select';
import InfinitePlaces from './InfinitePlaces';
import ToastMsg from '../ui/toast/ToastMsg';

import type { TPlace } from '@/types/response';
import { searchPlaces } from './action';

// 최신순 오래된순 평점순

const sortOptions = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  // {
  //   label: '별점순',
  //   value: 'rate',
  // },
];

export default function PlaceResults({
  data,
}: {
  data: { places: TPlace[]; hasNext: boolean };
}) {
  const [state, dispatch] = useContext(SearchContext);
  const [error, setError] = useState('');

  async function onChangeSelectOption(option: string) {
    dispatch({ type: 'SET_LOADING', payload: true });

    const result = await searchPlaces(state.keyword, 1, 5, option);

    if (result.status === 'failed') {
      setError(result.message);
      dispatch({ type: 'SET_LOADING', payload: false });
      setTimeout(() => setError(''), 2000);
      return;
    }

    dispatch({ type: 'SEARCH_PLACE', payload: result.data });
    dispatch({ type: 'SET_SORT_OPTION', payload: option });
    dispatch({ type: 'SET_LOADING', payload: false });
  }

  return (
    <div className="py-4 flex flex-col grow">
      <div className="px-4 flex justify-end">
        <Select
          list={sortOptions}
          selectOption={state.sortOptions}
          onChangeSelectOption={onChangeSelectOption}
          defaultValue={sortOptions[0].label}
        />
      </div>
      <InfinitePlaces
        initialContent={data.places}
        initialHasNext={data.hasNext}
      />
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </div>
  );
}
