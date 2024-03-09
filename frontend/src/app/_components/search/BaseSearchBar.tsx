'use client';

import { useRef, useContext } from 'react';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';

import { SearchContext } from '@/context/search/SearchContext';
import { searchPlaces } from './action';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

import type { EventFor } from '@/types/type';

/**
 * 로딩 상태 UI 필요
 */
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

    /* 추천 검색어 */
  }

  function handleErase() {
    dispatch({ type: 'EMPTY_KEYWORD' });
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

    switch (state.result.status) {
      case 'searchEngine': {
        // 네이버 장소 검색
        return;
      }

      case 'start': {
        dispatch({ type: 'SET_LOADING', payload: true });

        const json = await searchPlaces(state.keyword);

        dispatch({ type: 'SEARCH_PLACE', payload: json });
        dispatch({ type: 'SET_LOADING', payload: false });

        return;
      }

      case 'backend': {
        const { type } = state.result.data;

        if (type === 'course') {
          // 코스 검색
          return;
        }

        dispatch({ type: 'SET_LOADING', payload: true });

        const json = await searchPlaces(state.keyword);

        dispatch({ type: 'SEARCH_PLACE', payload: json });
        dispatch({ type: 'SET_LOADING', payload: false });
      }
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
