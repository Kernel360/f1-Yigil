'use client';

import { useContext, useEffect, useState } from 'react';

import ToastMsg from '@/app/_components/ui/toast/ToastMsg';
import Select from '@/app/_components/ui/select/Select';

import { getMySpots } from '../action';

import LoadingIndicator from '../../../LoadingIndicator';
import ExistPlaceItem from './ExistPlaceItem';

import type { TMyPageSpot } from '@/types/myPageResponse';
import { AddTravelExistsContext } from '@/context/exists/AddTravelExistsContext';
import Pagination from '@/app/_components/mypage/Pagination';

const sortOptions = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  {
    label: '별점순',
    value: 'rate',
  },
];

export default function AddCourseExistSpots() {
  const [addTravelExists, dispatchExists] = useContext(AddTravelExistsContext);
  const { selectedSpotsId } = addTravelExists;

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const [allSpots, setAllSpots] = useState<TMyPageSpot[]>([]);
  const [sortOption, setSortOption] = useState<string>('desc');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(1);

  function handleSelect(id: number) {
    if (selectedSpotsId.includes(id)) {
      dispatchExists({ type: 'UNSELECT_SPOT', payload: id });
      return;
    }

    dispatchExists({ type: 'SELECT_SPOT', payload: id });
  }

  async function getMySpotsFromBackend(
    pageNo: number = 1,
    size: number = 4,
    sortOrder?: string,
  ) {
    setIsLoading(true);

    const result = await getMySpots(pageNo, size, sortOrder);

    if (result.status === 'failed') {
      setError(result.message);
    } else {
      setAllSpots(result.data.content);
      setTotalPage(result.data.total_pages);
    }

    setTimeout(() => setError(''), 2000);
    setIsLoading(false);
  }

  function onChangeSelectOption(option: string | number) {
    if (typeof option === 'number') {
      return;
    }

    setAllSpots([]);
    setSortOption(option);
    setCurrentPage(1);
    getMySpotsFromBackend(1, 4, option);
  }

  useEffect(() => {
    getMySpotsFromBackend();
  }, []);

  useEffect(() => {
    getMySpotsFromBackend(currentPage);
  }, [currentPage]);

  return (
    <section className="flex flex-col grow">
      <div className="flex justify-end">
        <Select
          list={sortOptions}
          selectOption={sortOption}
          defaultValue={sortOptions[0].label}
          onChangeSelectOption={onChangeSelectOption}
        />
      </div>
      {isLoading ? (
        <div className="flex justify-center items-center grow">
          <LoadingIndicator loadingText="데이터 로딩 중입니다..." />
        </div>
      ) : (
        allSpots.map((spot) => (
          <ExistPlaceItem
            key={spot.spot_id}
            checked={selectedSpotsId.includes(spot.spot_id)}
            handleSelect={() => handleSelect(spot.spot_id)}
            spot={spot}
          />
        ))
      )}
      <Pagination
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPage={totalPage}
      />
      {error && <ToastMsg title={error} id={Date.now()} timer={2000} />}
    </section>
  );
}
