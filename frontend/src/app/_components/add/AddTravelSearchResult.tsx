'use client';

import { useContext, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import AddTravelSearchItem from './AddTravelSelectItem';
import ToastMsg from '../ui/toast/ToastMsg';

export default function AddTravelSearchResult() {
  const [state] = useContext(SearchContext);
  const [error, setError] = useState('');

  function invokeError(err: string) {
    setError(err);

    setTimeout(() => setError(''), 2000);
  }

  if (state.results.status === 'start') {
    return (
      <section className="absolute w-full h-full bg-white flex flex-col grow justify-center items-center gap-8">
        <span className="text-6xl">🔍</span>
        <br />
        <span className="text-3xl">원하는 장소를 검색해보세요!</span>
      </section>
    );
  }

  if (state.results.status === 'failed') {
    return (
      <section className="absolute w-full h-full bg-white flex flex-col grow justify-center items-center gap-8">
        <span className="text-6xl">🔍</span>
        <br />
        <span className="text-3xl">서버에서 값을 가져올 수 없습니다.</span>
      </section>
    );
  }

  if (state.results.content.from === 'backend') {
    console.error('알 수 없는 오류입니다');
    return (
      <section className="absolute w-full h-full bg-white flex flex-col grow justify-center items-center gap-8">
        <span className="text-6xl">🔍</span>
        <br />
        <span className="text-3xl">알 수 없는 오류입니다.</span>
      </section>
    );
  }

  const places = state.results.content.places;

  return (
    <section className="absolute px-6 w-full h-full bg-white grow">
      {places.map((place, index) => (
        <AddTravelSearchItem
          key={`${place.name}-${place.roadAddress}-${index}`}
          place={place}
          setError={invokeError}
        />
      ))}
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </section>
  );
}
