'use client';

import { useCallback, useEffect, useRef, useState } from 'react';

import Course from './Course';
import Spinner from '../../ui/Spinner';

import { getCourses } from '@/app/(with-header)/place/[id]/@reviews/action';

import type { TCourse } from '@/types/response';

export default function Courses({
  placeId,
  initialCourses,
  initialHasNext,
}: {
  placeId: number;
  initialCourses: TCourse[];
  initialHasNext: boolean;
}) {
  const endRef = useRef<HTMLDivElement>(null);

  const [courses, setCourses] = useState(initialCourses);
  const [currentPage, setCurrentPage] = useState(1);
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

      const result = await getCourses(placeId, nextPage);

      if (result.status === 'failed') {
        setError('리뷰를 가져올 수 없습니다!');
        setTimeout(() => setError(''), 2000);
        return;
      }

      setHasNext(result.data.has_next);
      setCourses([...courses, ...result.data.courses]);
      setCurrentPage(nextPage);
      setIsLoading(false);
    },
    [currentPage, courses, placeId],
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

  if (courses.length === 0) {
    return (
      <section className="w-full aspect-square flex flex-col justify-center items-center text-center">
        <div className="flex flex-col gap-6">
          <span className="text-6xl">🍃</span>
          <span className="text-2xl break-keep">
            코스에 대한 리뷰가 없습니다.
          </span>
        </div>
      </section>
    );
  }

  return (
    <section className="pt-4">
      {courses.map((course, index) => (
        <Course key={index} placeId={placeId} data={course} />
      ))}
      <div
        className="mt-2 min-h-6 bg-gray-200 flex justify-center items-center"
        ref={endRef}
      >
        <div className="py-4">
          {hasNext ? (
            isLoading ? (
              <Spinner />
            ) : (
              <span className="font-medium">아래로 내려 더 보기</span>
            )
          ) : (
            <span className="font-medium">마지막 결과입니다!</span>
          )}
        </div>
      </div>
    </section>
  );
}
