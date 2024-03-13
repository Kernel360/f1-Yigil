'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { searchPlaces } from './action';

import LoadingIndicator from '../LoadingIndicator';
import InfinitePlaces from './InfinitePlaces';

import type { EventFor } from '@/types/type';

export default function TravelSearchResult() {
  const [state, dispatch] = useContext(SearchContext);

  const { status } = state.result;

  if (status !== 'backend') {
    return null;
  }

  async function handlePlaceButton(event: EventFor<'button', 'onClick'>) {
    if (status === 'backend' && state.result.data.type === 'place') {
      return;
    }

    dispatch({ type: 'SET_LOADING', payload: true });

    const json = await searchPlaces(state.keyword);

    dispatch({ type: 'SEARCH_PLACE', payload: json });
    dispatch({ type: 'SET_LOADING', payload: false });
  }

  function handleCourseButton(event: EventFor<'button', 'onClick'>) {
    if (status === 'backend' && state.result.data.type === 'course') {
      return;
    }

    dispatch({ type: 'SEARCH_COURSE' });
  }

  const { data } = state.result;

  return (
    <section className="flex flex-col grow">
      <div className="flex">
        <button
          className={`py-4 grow ${
            data.type === 'place' && 'border-black border-b-2'
          }`}
          onClick={handlePlaceButton}
        >
          장소
        </button>
        <button
          className={`py-4 grow ${
            data.type === 'course' && 'border-black border-b-2'
          }`}
          onClick={handleCourseButton}
        >
          코스
        </button>
      </div>
      {state.loading ? (
        <section className="grow flex flex-col justify-center items-center gap-8">
          <LoadingIndicator loadingText="검색 중..." />
        </section>
      ) : data.type === 'place' ? (
        <InfinitePlaces
          content={data.content}
          hasNext={data.hasNext}
          currentPage={data.currentPage}
        />
      ) : (
        <section className="grow flex flex-col justify-center items-center gap-8">
          <span className="text-6xl">🚧</span>
          <br />
          <span className="text-5xl">준비 중입니다!</span>
        </section>
      )}
    </section>
  );
}
