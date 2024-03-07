'use client';

import { useCallback, useEffect, useRef, useState } from 'react';
import Spot from './Spot';
import { getSpots } from '@/app/(with-header)/place/[id]/@reviews/action';

import type { TSpot } from '@/types/response';

export default function Spots({
  placeId,
  initialPage,
  initialSpots,
  initialHasNext,
}: {
  placeId: number;
  initialPage: number;
  initialSpots: TSpot[];
  initialHasNext: boolean;
}) {
  const endRef = useRef<HTMLDivElement>(null);

  const [spots, setSpots] = useState(initialSpots);
  const [currentPage, setCurrentPage] = useState(initialPage);
  const [hasNext, setHasNext] = useState(initialHasNext);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const onScroll: IntersectionObserverCallback = useCallback(
    async (entries) => {
      if (!entries[0].isIntersecting) {
        return;
      }

      setError('');
      setIsLoading(true);

      const nextPage = currentPage + 1;

      const result = await getSpots(placeId, nextPage);

      if (!result.success) {
        setError('리뷰를 가져올 수 없습니다!');
        return;
      }

      setHasNext(result.data.has_next);
      setSpots(result.data.spots);
      setCurrentPage(nextPage);
      setIsLoading(false);
    },
    [currentPage, placeId],
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
  }, [endRef, hasNext, onScroll]);

  return (
    <section className="pt-6">
      {spots.map((spot, index) => (
        <Spot placeId={placeId} key={index} data={spot} />
      ))}
      {error}
      <div
        className="min-h-6 bg-gray-200 flex justify-center items-center"
        ref={endRef}
      >
        {hasNext ? isLoading ? <>Loading...</> : <>Load?</> : <></>}
      </div>
    </section>
  );
}
