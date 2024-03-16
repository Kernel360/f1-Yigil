'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import type { EventFor } from '@/types/type';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';
import { searchPlaces } from './action';

export default function SearchItem({
  item,
  erasable,
  onClick,
}: {
  item: string;
  erasable?: boolean;
  onClick?: (item: string) => void;
}) {
  const [, dispatch] = useContext(SearchContext);

  async function handleClick(event: EventFor<'button', 'onClick'>) {
    onClick && onClick(item);

    // dispatch({ type: 'SET_KEYWORD', payload: item });
    // const json = await searchPlaces(item);
    // dispatch({ type: 'SEARCH_PLACE', payload: json });
  }

  return (
    <div className={`${!erasable && 'py-3'} flex justify-between`}>
      <button className="flex gap-1 items-center grow" onClick={handleClick}>
        <SearchIcon className="w-6 h-6 mr-4" />
        <span className="text-xl font-light">{item}</span>
      </button>
      {erasable && (
        <button
          className="p-3"
          onClick={() => dispatch({ type: 'DELETE_HISTORY', payload: item })}
        >
          <XMarkIcon className="w-4 h-4" />
        </button>
      )}
    </div>
  );
}
