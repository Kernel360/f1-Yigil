'use client';
import { TMyPagePlace } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import ToastMsg from '../../ui/toast/ToastMsg';
import LoadingIndicator from '../../LoadingIndicator';
import useIntersectionObserver from '../../hooks/useIntersectionObserver';
import { scrollToTop } from '@/utils';
import MyPagePlaceItem from '../MyPagePlaceItem';
import { getFavoritePlaces } from './favoriteActions';
import LikeButton from '../../course/LikeButton';

const sortOptions = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  { label: '이름순', value: 'place_name' },
  { label: '별점순', value: 'rate' },
];

export default function FavoritePlaceList({
  favoritePlaceList,
  has_next,
}: {
  favoritePlaceList: TMyPagePlace[];
  has_next: boolean;
}) {
  const [sortOption, setSortOption] = useState('desc');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;
  const [allFavoritePlaceList, setAllFavoritePlaceList] =
    useState(favoritePlaceList);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [hasNext, setHasNext] = useState(has_next);

  const ref = useRef<HTMLDivElement>(null);

  const getMoreBookMarks = async () => {
    setCurrentPage((prev) => (prev += 1));
    const FavoritePlaceList = await getFavoritePlaces(
      currentPage + 1,
      divideCount,
      sortOption,
    );
    if (!FavoritePlaceList.success) {
      setErrorText('북마크 데이터를 불러오는데 실패했습니다.');
      setIsLoading(false);
      return;
    }
    setHasNext((prev) => (prev = favoritePlaceList.data.has_next));
    setAllFavoritePlaceList((prev) => [
      ...prev,
      ...favoritePlaceList.data.bookmarks,
    ]);
  };

  useIntersectionObserver(ref, getMoreBookMarks, hasNext);

  const onChangeSortOption = (option: string) => {
    setSortOption(option);
  };

  const getBookmarks = async (
    pageNum: number,
    size: number,
    sortOrder: string,
  ) => {
    try {
      setIsLoading(true);
      const favoritePlaceList = await getFavoritePlaces(
        pageNum,
        size,
        sortOrder,
      );
      if (!favoritePlaceList.success) {
        setAllFavoritePlaceList([]);
        setErrorText('북마크 데이터를 불러오는데 실패했습니다.');
        setIsLoading(false);
        return;
      }
      setHasNext((prev) => (prev = favoritePlaceList.data.has_next));
      setAllFavoritePlaceList([...favoritePlaceList.data.bookmarks]);
    } catch (error) {
      setErrorText('북마크 데이터를 불러오는데 실패했습니다.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    setCurrentPage(1);
    scrollToTop();
    getBookmarks(1, divideCount, sortOption);
  }, [sortOption]);

  return (
    <div className="px-4">
      <div className="flex justify-end mt-6 mb-4">
        <Select
          list={sortOptions}
          selectStyle="p-2"
          selectOption={sortOption}
          onChangeSelectOption={onChangeSortOption}
          defaultValue="최신순"
        />
      </div>

      {allFavoritePlaceList.map(({ place_id, ...bookmark }, idx) => (
        <div
          key={`${place_id}-${idx}`}
          className={`flex items-center border-b-2 ${
            idx === 0 && 'border-t-2'
          }`}
        >
          <MyPagePlaceItem place_id={place_id} {...bookmark} />
          <LikeButton
            travelId={place_id}
            liked={true}
            isLoggedIn="true"
            sizes="w-5 h-5"
            position=""
          />
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
      {errorText && <ToastMsg title={errorText} timer={1000} />}
    </div>
  );
}
