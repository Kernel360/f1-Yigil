'use client';

import { useContext, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import Places from '../place/Places';
import { MemberContext } from '@/context/MemberContext';
import { EventFor } from '@/types/type';

export default function TravelSearchResult() {
  const { isLoggedIn } = useContext(MemberContext);
  const { state, dispatch } = useContext(SearchContext);

  const { from } = state.result;

  if (from !== 'backend') {
    return null;
  }

  function handlePlaceButton(event: EventFor<'button', 'onClick'>) {
    if (from === 'backend' && state.result.data.type === 'place') {
      return;
    }

    dispatch({ type: 'SEARCH_PLACE' });
  }

  function handleCourseButton(event: EventFor<'button', 'onClick'>) {
    if (from === 'backend' && state.result.data.type === 'course') {
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
      {data.type === 'place' ? (
        // Infinite Scroll Place
        <Places data={data.content} isLoggedIn={isLoggedIn === 'true'} />
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
