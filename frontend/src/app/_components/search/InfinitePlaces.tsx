'use client';

import { useCallback, useContext, useEffect, useRef, useState } from 'react';
import { Place } from '../place/places';

import { SearchContext } from '@/context/search/SearchContext';
import { MemberContext } from '@/context/MemberContext';
import { searchPlaces } from './action';

import type { TPlace } from '@/types/response';

export default function InfinitePlaces({
  initialContent,
  initialHasNext,
}: {
  initialContent: TPlace[];
  initialHasNext: boolean;
  // content: TPlace[];
  // hasNext: boolean;
  // currentPage: number;
}) {
  const [state, dispatch] = useContext(SearchContext);
  const { isLoggedIn } = useContext(MemberContext);

  const [isLoading, setIsLoading] = useState(false);
  const endRef = useRef<HTMLDivElement>(null);

  const [content, setContent] = useState(initialContent);
  const [hasNext, setHasNext] = useState(initialHasNext);
  const [currentPage, setCurrentPage] = useState(1);

  const onScroll: IntersectionObserverCallback = useCallback(
    async (entries) => {
      if (!entries[0].isIntersecting) {
        return;
      }

      setIsLoading(true);

      const result = await searchPlaces(state.keyword, currentPage + 1);

      if (result.status === 'failed') {
        // Toast
        console.log(result.message);
        setIsLoading(false);
        return;
      }

      setContent([...content, ...result.data.places]);
      setCurrentPage(currentPage + 1);
      setHasNext(result.data.has_next);

      setIsLoading(false);
    },
    [state.keyword, currentPage, content],
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
