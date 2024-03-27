'use client';

import { useState } from 'react';

import Spot from './Spot';
import Spinner from '../../ui/Spinner';

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
  const [spots, setSpots] = useState(initialSpots);
  const [currentPage, setCurrentPage] = useState(initialPage);
  const [hasNext, setHasNext] = useState(initialHasNext);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  async function getMoreSpots() {
    setIsLoading(true);

    const nextPage = currentPage + 1;

    const result = await getSpots(placeId, nextPage);

    if (result.status === 'failed') {
      setError('리뷰를 가져올 수 없습니다!');
      setIsLoading(false);
      setTimeout(() => setError(''), 2000);
      return;
    }

    setHasNext(result.data.has_next);
    setSpots([...spots, ...result.data.spots]);
    setCurrentPage(nextPage);
    setIsLoading(false);
  }

  if (spots.length === 0) {
    return (
      <section className="w-full aspect-square flex flex-col justify-center items-center text-center">
        <div className="flex flex-col gap-6">
          <span className="text-6xl">🍃</span>
          <span className="text-2xl break-keep">
            장소에 대한 리뷰가 없습니다.
          </span>
        </div>
      </section>
    );
  }

  return (
    <section className="pt-4">
      {spots.map((spot, index) => (
        <Spot placeId={placeId} key={index} data={spot} />
      ))}
      {hasNext && (
        <div className="flex">
          <button
            className="py-4 w-full bg-gray-200 flex justify-center items-center"
            onClick={getMoreSpots}
          >
            {isLoading ? (
              <Spinner />
            ) : (
              <span className="text-lg font-medium">더보기</span>
            )}
          </button>
        </div>
      )}
      {error}
    </section>
  );
}
