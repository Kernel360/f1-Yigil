'use client';

import { useCallback, useContext, useEffect, useRef, useState } from 'react';
import { Place } from '../place';

import { SearchContext } from '@/context/search/SearchContext';
import { MemberContext } from '@/context/MemberContext';
import { searchPlaces } from './action';

import type { TPlace } from '@/types/response';

export default function InfinitePlaces({
  content,
  hasNext,
  currentPage,
}: {
  content: TPlace[];
  hasNext: boolean;
  currentPage: number;
}) {
  const [state, dispatch] = useContext(SearchContext);
  const { isLoggedIn } = useContext(MemberContext);

  const [isLoading, setIsLoading] = useState(false);
  const endRef = useRef<HTMLDivElement>(null);

  const onScroll: IntersectionObserverCallback = useCallback(
    async (entries) => {
      if (!entries[0].isIntersecting) {
        return;
      }

      setIsLoading(true);

      const json = await searchPlaces(state.keyword, currentPage + 1);

      dispatch({
        type: 'MORE_PLACE',
        payload: { json, nextPage: currentPage + 1 },
      });

      setIsLoading(false);
    },
    [state.keyword, currentPage, dispatch],
  );

  useEffect(() => {
    if (!endRef.current) {
      return;
    }

    const observer = new IntersectionObserver(onScroll, { threshold: 0.9 });
    observer.observe(endRef.current);

    if (!hasNext) {
      observer.unobserve(endRef.current);
    }

    return () => {
      observer.disconnect();
    };
  }, [onScroll, hasNext]);

  if (content.length === 0) {
    return (
      <section className="flex flex-col grow justify-center items-center gap-8">
        <span className="text-6xl">⚠️</span>
        <br />
        <span className="text-3xl">검색 결과가 없습니다.</span>
      </section>
    );
  }

  return (
    <section className="flex flex-col grow">
      {content.map((item, index) => (
        <Place
          key={item.id}
          data={item}
          isLoggedIn={isLoggedIn === 'true'}
          order={index}
        />
      ))}
      <div
        className="min-h-6 bg-gray-200 flex justify-center items-center"
        ref={endRef}
      >
        {hasNext ? isLoading ? <>Loading...</> : <>Load?</> : <></>}
      </div>
    </section>
  );
}
