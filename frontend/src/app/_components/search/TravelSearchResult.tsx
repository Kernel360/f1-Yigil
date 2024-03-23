'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { searchPlaces } from './action';

import LoadingIndicator from '../LoadingIndicator';
import InfinitePlaces from './InfinitePlaces';

import type { EventFor } from '@/types/type';
import BaseSearchHistory from './BaseSearchHistory';
import KeywordSuggestion from './KeywordSuggestion';

export default function TravelSearchResult() {
  const [state, dispatch] = useContext(SearchContext);

  const { results, loading, keyword, backendSearchType } = state;

  // 추천 검색어 뜨는 경우 조정
  if (results.status === 'start') {
    if (keyword.length === 0) {
      return <BaseSearchHistory />;
    }

    if (loading) {
      return (
        <section className="grow flex flex-col justify-center items-center gap-8">
          <LoadingIndicator loadingText="검색 중..." />
        </section>
      );
    }

    return <KeywordSuggestion />;
  }

  if (results.status === 'failed') {
    return (
      <section className="flex flex-col grow justify-center items-center gap-8">
        <span className="text-6xl">⚠️</span>
        <br />
        <span className="text-3xl">서버 연결에 실패했습니다.</span>
      </section>
    );
  }

  if (results.content.from === 'searchEngine') {
    return null;
  }

  const content = results.content;

  async function handlePlaceButton(event: EventFor<'button', 'onClick'>) {
    if (content.from === 'backend' && content.data.type === 'place') {
      return;
    }

    dispatch({ type: 'SET_LOADING', payload: true });

    const json = await searchPlaces(state.keyword);

    dispatch({ type: 'SEARCH_PLACE', payload: json });
    dispatch({ type: 'SET_LOADING', payload: false });
  }

  function handleCourseButton(event: EventFor<'button', 'onClick'>) {
    if (content.from === 'backend' && content.data.type === 'course') {
      return;
    }

    dispatch({ type: 'SEARCH_COURSE' });
  }

  const data = content.data;

  return (
    <section className="flex flex-col grow">
      <div className="flex">
        <button
          className={`py-4 grow ${
            backendSearchType === 'place' && 'border-black border-b-2'
          }`}
          onClick={handlePlaceButton}
        >
          장소
        </button>
        <button
          className={`py-4 grow ${
            backendSearchType === 'course' && 'border-black border-b-2'
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
          content={data.places}
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
