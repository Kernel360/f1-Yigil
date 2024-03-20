'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

export default function BaseSearchItem({
  label,
  erasable,
  onSelect,
}: {
  label: string;
  erasable?: boolean;
  onSelect?: () => Promise<void>;
}) {
  const [, dispatch] = useContext(SearchContext);

  async function handleSelect() {
    onSelect && (await onSelect());
  }

  async function handleErase() {
    dispatch({ type: 'DELETE_HISTORY', payload: label });
  }

  return (
    <div className={`${!erasable && 'py-3'} flex justify-between`}>
      <button className="flex gap-1 items-center grow" onClick={handleSelect}>
        <SearchIcon className="w-6 h-6 mr-4 shrink-0" />
        <span className="text-xl text-start font-light">{label}</span>
      </button>
      {erasable && (
        <button className="p-3" onClick={handleErase}>
          <XMarkIcon className="w-4 h-4" />
        </button>
      )}
    </div>
  );
}
