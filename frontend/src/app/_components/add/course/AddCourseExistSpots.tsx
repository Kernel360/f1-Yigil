'use client';

import { useContext, useEffect, useState } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

import ToastMsg from '@/app/_components/ui/toast/ToastMsg';
import Select from '@/app/_components/ui/select/Select';

import { getMySpots } from './action';

import LoadingIndicator from '../../LoadingIndicator';
import ExistPlaceItem from './ExistPlaceItem';

import type { TMyPageSpot } from '@/types/myPageResponse';
import type { TSpotState } from '@/context/travel/schema';
import { number } from 'zod';

const sortOptions = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  {
    label: '별점순',
    value: 'rate',
  },
];

export default function AddCourseExistSpots() {
  const [course, dispatchCourse] = useContext(CourseContext);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const [allSpots, setAllSpots] = useState<TMyPageSpot[]>([]);
  const [sortOption, setSortOption] = useState<string>('desc');

  async function getMySpotsFromBackend(
    pageNo: number = 1,
    size: number = 5,
    sortOrder?: string,
  ) {
    setIsLoading(true);

    const result = await getMySpots(pageNo, size, sortOrder);

    console.log(result);

    if (result.status === 'failed') {
      setError(result.message);
    } else {
      setAllSpots(result.data.content);
    }

    setTimeout(() => setError(''), 2000);
    setIsLoading(false);
  }

  function onChangeSelectOption(option: string) {
    setAllSpots([]);
    setSortOption(option);
    getMySpotsFromBackend(1, 5, option);
  }

  useEffect(() => {
    getMySpotsFromBackend();
  }, []);

  return (
    <section className="flex flex-col grow">
      {isLoading && (
        <div className="flex justify-center items-center grow">
          <LoadingIndicator loadingText="데이터 로딩 중입니다..." />
        </div>
      )}
      <div className="flex justify-end">
        <Select
          list={sortOptions}
          selectOption={sortOption}
          defaultValue={sortOptions[0].label}
          onChangeSelectOption={onChangeSelectOption}
          selectStyle="p-2"
        />
      </div>
      {allSpots.map((spot) => (
        <ExistPlaceItem key={spot.spot_id} spot={spot} />
      ))}
      {error && <ToastMsg title={error} id={Date.now()} timer={2000} />}
    </section>
  );
}
