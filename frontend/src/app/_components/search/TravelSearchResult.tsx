'use client';

import { useContext, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { searchCourses, searchPlaces } from './action';

import LoadingIndicator from '../LoadingIndicator';
import InfinitePlaces from './InfinitePlaces';
import InfiniteCourses from './InfiniteCourses';
import BaseSearchHistory from './BaseSearchHistory';
import KeywordSuggestion from './KeywordSuggestion';

import type { EventFor } from '@/types/type';

function checkBatchimEnding(word: string) {
  const lastLetter = word[word.length - 1];
  const uni = lastLetter.charCodeAt(0);

  if (uni < 44032 || uni > 55203) return false;

  return (uni - 44032) % 28 != 0;
}

export default function TravelSearchResult() {
  const [state, dispatch] = useContext(SearchContext);

  const { results, loading, keyword, backendSearchType } = state;

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

  async function handleCourseButton(event: EventFor<'button', 'onClick'>) {
    if (content.from === 'backend' && content.data.type === 'course') {
      return;
    }

    dispatch({ type: 'SET_LOADING', payload: true });

    const json = await searchCourses(state.keyword);

    dispatch({ type: 'SEARCH_COURSE', payload: json });
    dispatch({ type: 'SET_LOADING', payload: false });
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
        <div className="flex grow">
          <InfinitePlaces
            content={data.places}
            hasNext={data.hasNext}
            currentPage={data.currentPage}
          />
        </div>
      ) : (
        <div className="py-4 flex flex-col grow">
          <span className="px-4 text-gray-500">{`'${state.currentTerm}'${
            checkBatchimEnding(state.currentTerm) ? '이' : '가'
          } 포함된 코스를 모두 검색하였습니다.`}</span>
          <InfiniteCourses
            content={data.courses}
            hasNext={data.hasNext}
            currentPage={data.currentPage}
          />
        </div>
      )}
    </section>
  );
}
