'use client';

import { useRef, useState, useContext, useEffect } from 'react';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';

import { SearchContext } from '@/context/search/SearchContext';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

import type { EventFor } from '@/types/type';
import { searchPlaces } from './action';

export default function BaseSearchBar({
  cancellable,
}: {
  cancellable?: boolean;
}) {
  const inputRef = useRef<HTMLInputElement>(null);

  const { state, dispatch } = useContext(SearchContext);

  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { push, replace } = useRouter();

  function handleChange(term: string) {
    dispatch({ type: 'SET_KEYWORD', payload: term });

    const params = new URLSearchParams(searchParams);

    if (term) {
      params.set('keyword', term);
    } else {
      params.delete('keyword');
    }

    replace(`${pathname}?${params.toString()}`);

    /* 추천 검색어 */
  }

  function handleErase() {
    dispatch({ type: 'SET_KEYWORD', payload: '' });
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
    if (state.keyword === '') {
      return;
    }

    dispatch({ type: 'ADD_HISTORY' });

    const result = await searchPlaces(state.keyword);

    // console.log(result);

    // if (!result.success) {
    //   console.log(result.error.message);
    //   return;
    // }

    dispatch({ type: 'SEARCH_PLACE' });
    // dispatch({ type: 'SEARCH_PLACE', payload: result.data });
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
          placeholder="검색어를 입력하세요."
          value={state.keyword}
          onChange={(event) => handleChange(event.target.value)}
          onKeyDown={handleEnter}
        />
        {searchParams.size !== 0 && (
          <button
            className="p-1 bg-gray-400 rounded-full flex justify-center items-center"
            onClick={handleErase}
          >
            <XMarkIcon className="w-3 h-3 stroke-white stroke-[1.25]" />
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
