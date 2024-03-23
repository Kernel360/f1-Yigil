'use client';

import { useRef, useContext } from 'react';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';

import { SearchContext } from '@/context/search/SearchContext';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

import type { EventFor } from '@/types/type';

export default function BaseSearchBar({
  onCancel,
  onFocus,
  onSearch,
  placeholder,
  cancellable,
}: {
  onCancel: () => void;
  onFocus?: () => void;
  onSearch: (term: string) => Promise<void>;
  placeholder?: string;
  cancellable?: boolean;
}) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [state, dispatch] = useContext(SearchContext);

  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { push, replace } = useRouter();

  function handleChange(term: string) {
    const params = new URLSearchParams(searchParams);

    if (term.length === 0) {
      params.delete('keyword');
      replace(`${pathname}?${params.toString()}`);
      dispatch({ type: 'EMPTY_KEYWORD' });
      return;
    }

    params.set('keyword', term);
    replace(`${pathname}?${params.toString()}`);
    dispatch({ type: 'SET_KEYWORD', payload: term });
  }

  function handleErase() {
    onCancel();
    replace(`${pathname}`);
    inputRef.current?.focus();
  }

  function handleCancel() {
    if (pathname === '/search') {
      push('/');
    } else {
      handleErase();
    }
  }

  async function handleSearch() {
    if (state.keyword === '' || state.keyword.trim() === '') {
      return;
    }

    dispatch({ type: 'ADD_HISTORY' });
    dispatch({ type: 'SET_LOADING', payload: true });

    try {
      await onSearch(state.keyword);
    } catch (error) {
      // Toast
      console.error(error);
    } finally {
      dispatch({ type: 'SET_LOADING', payload: false });
    }
  }

  async function handleEnter(event: EventFor<'input', 'onKeyDown'>) {
    if (event.key !== 'Enter') {
      return;
    }

    await handleSearch();
  }

  return (
    <div className="flex gap-4 px-6">
      <div className="w-full px-4 py-2 bg-[#e5e7eb] shadow-lg rounded-full flex gap-4 items-center">
        <input
          className="w-full text-lg font-light bg-transparent outline-none focus:border-b-2 focus:border-black"
          ref={inputRef}
          type="text"
          placeholder={placeholder}
          value={state.keyword}
          onChange={(event) => handleChange(event.target.value)}
          onKeyDown={handleEnter}
          onFocus={onFocus}
        />
        {searchParams.size !== 0 && (
          <button
            className="p-[5px] bg-gray-400 rounded-full flex justify-center items-center"
            onClick={handleErase}
          >
            <XMarkIcon className="w-4 h-4 stroke-white stroke-[1.25]" />
          </button>
        )}
        <button onClick={handleSearch}>
          <SearchIcon className="w-6 h-6 shrink-0" />
        </button>
      </div>
      {cancellable && (
        <button
          className="shrink-0 font-light text-gray-400"
          onClick={handleCancel}
        >
          취소
        </button>
      )}
    </div>
  );
}
