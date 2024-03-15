'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import AddTravelSearchItem from './AddTravelSelectItem';

export default function AddTravelSearchResult() {
  const [state] = useContext(SearchContext);

  if (state.results.status === 'start') {
    return (
      <section className="absolute w-full h-full bg-white flex flex-col grow justify-center items-center">
        장소를 검색해주세요.
      </section>
    );
  }

  if (state.results.status === 'failed') {
    return <>서버에서 값을 가져올 수 없습니다.</>;
  }

  if (state.results.content.from === 'backend') {
    console.error('알 수 없는 오류입니다');
    return <>알 수 없는 오류입니다.</>;
  }

  const places = state.results.content.places;

  return (
    <section className="absolute px-6 w-full h-full bg-white grow">
      {places.map((place) => (
        <AddTravelSearchItem
          key={`${place.name}-${place.roadAddress}`}
          place={place}
        />
      ))}
    </section>
  );
}
