'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { searchCourses, searchPlaces } from './action';

import LoadingIndicator from '../LoadingIndicator';
import BaseSearchHistory from './BaseSearchHistory';
import KeywordSuggestion from './KeywordSuggestion';

import PlaceResults from './PlaceResults';
import CourseResults from './CourseResults';

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

  async function handlePlaceButton() {
    if (content.from === 'backend' && content.data.type === 'place') {
      return;
    }

    dispatch({ type: 'SET_LOADING', payload: true });

    const result = await searchPlaces(state.keyword, 1, 5, state.sortOptions);

    if (result.status === 'failed') {
      // Toast
      dispatch({ type: 'SET_LOADING', payload: false });
      return;
    }

    dispatch({ type: 'SEARCH_PLACE', payload: result.data });
    dispatch({ type: 'SET_LOADING', payload: false });
  }

  async function handleCourseButton() {
    if (content.from === 'backend' && content.data.type === 'course') {
      return;
    }

    dispatch({ type: 'SET_LOADING', payload: true });

    const result = await searchCourses(state.keyword, 1, 5, state.sortOptions);

    if (result.status === 'failed') {
      // Toast
      dispatch({ type: 'SET_LOADING', payload: false });
      return;
    }

    dispatch({ type: 'SEARCH_COURSE', payload: result.data });
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
        <PlaceResults data={data} />
      ) : (
        <CourseResults data={data} />
      )}
    </section>
  );
}
