'use client';
import { TMyPageFavoriteSpot, TMyPagePlace } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import ToastMsg from '../../ui/toast/ToastMsg';
import LoadingIndicator from '../../LoadingIndicator';
import useIntersectionObserver from '../../hooks/useIntersectionObserver';
import { scrollToTop } from '@/utils';

import LikeButton from '../../course/LikeButton';
import { getFavoriteSpots } from './FavoriteActions';
import FavoriteSpotItem from './FavoriteSpotItem';

const sortOptions = [
  { label: '이름순', value: 'place_name' },
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
];

export default function FavoriteSpotList({
  favoriteSpotList,
  has_next,
}: {
  favoriteSpotList: TMyPageFavoriteSpot['contents'];
  has_next: boolean;
}) {
  const [sortOption, setSortOption] = useState('place_name');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;
  const [allFavoriteSpotList, setAllFavoriteSpotList] =
    useState<TMyPageFavoriteSpot['contents']>(favoriteSpotList);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [hasNext, setHasNext] = useState(has_next);

  const ref = useRef<HTMLDivElement>(null);

  const getMoreSpots = async () => {
    const favoriteSpotList = await getFavoriteSpots(
      currentPage + 1,
      divideCount,
      sortOption,
    );
    if (favoriteSpotList.status === 'failed') {
      setErrorText('스팟 데이터를 불러오는데 실패했습니다.');
      setTimeout(() => {
        setErrorText('');
      }, 9000);
      setIsLoading(false);
      return;
    }
    setHasNext(favoriteSpotList.data.has_next);
    setAllFavoriteSpotList((prev) => [
      ...prev,
      ...favoriteSpotList.data.contents,
    ]);
    setCurrentPage((prev) => (prev += 1));
  };

  useIntersectionObserver(ref, getMoreSpots, hasNext);

  const onChangeSortOption = (option: string) => {
    setSortOption(option);
  };

  const getSpots = async (pageNum: number, size: number, sortOrder: string) => {
    setIsLoading(true);

    const favoriteSpotList = await getFavoriteSpots(pageNum, size, sortOrder);
    if (favoriteSpotList.status === 'failed') {
      setAllFavoriteSpotList([]);
      setErrorText('스팟 데이터를 불러오는데 실패했습니다.');
      setTimeout(() => {
        setErrorText('');
      }, 9000);
      setIsLoading(false);
      return;
    }
    setHasNext((prev) => (prev = favoriteSpotList.data.has_next));
    setAllFavoriteSpotList([...favoriteSpotList.data.contents]);
    setIsLoading(false);
  };

  useEffect(() => {
    setCurrentPage(1);
    scrollToTop();
    getSpots(1, divideCount, sortOption);
  }, [sortOption]);

  return (
    <div className="px-4">
      <div className="flex justify-end mt-6 mb-4">
        <Select
          list={sortOptions}
          selectStyle="p-2"
          selectOption={sortOption}
          onChangeSelectOption={onChangeSortOption}
          defaultValue="이름순"
        />
      </div>

      {allFavoriteSpotList.map(({ spot_id, ...spot }, idx) => (
        <div
          key={`${spot_id}-${idx}`}
          className={`flex items-center border-b-2 py-6 ${
            idx === 0 && 'border-t-2'
          }`}
        >
          <div className="flex-[5]">
            <FavoriteSpotItem spot_id={spot_id} {...spot} />
          </div>
          <div className="relative flex-[1] flex justify-end">
            <LikeButton
              travelId={spot_id}
              liked={true}
              sizes="w-9 h-9"
              position="top-[-20px]"
              isLoggedIn="true"
            />
          </div>
        </div>
      ))}

      <div className="flex justify-center my-8" ref={ref}>
        {hasNext &&
          (isLoading ? (
            <LoadingIndicator loadingText="데이터 로딩중" />
          ) : (
            <button className="py-1 px-8 bg-gray-200 rounded-lg">더보기</button>
          ))}
      </div>
      {errorText && <ToastMsg title={errorText} timer={9000} />}
    </div>
  );
}
