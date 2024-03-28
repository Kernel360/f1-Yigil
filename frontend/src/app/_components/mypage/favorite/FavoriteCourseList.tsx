('use client');
import { TMyPageCourse, TMyPagePlace } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import ToastMsg from '../../ui/toast/ToastMsg';
import LoadingIndicator from '../../LoadingIndicator';
import useIntersectionObserver from '../../hooks/useIntersectionObserver';
import { scrollToTop } from '@/utils';

import LikeButton from '../../course/LikeButton';
import MyPagePlaceItem from '../bookmark/MyPagePlaceItem';
import { getFavoriteCourses, getFavoritePlaces } from './FavoriteActions';

const sortOptions = [
  { label: '이름순', value: 'place_name' },
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  { label: '별점순', value: 'rate' },
];

export default function FavoriteCourseList({
  favoriteCourseList,
  has_next,
}: {
  favoriteCourseList: TMyPageCourse[];
  has_next: boolean;
}) {
  const [sortOption, setSortOption] = useState('desc');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;
  const [allFavoriteCourseList, setAllFavoriteCourseList] =
    useState(favoriteCourseList);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [hasNext, setHasNext] = useState(has_next);

  const ref = useRef<HTMLDivElement>(null);

  const getMoreBookMarks = async () => {
    setCurrentPage((prev) => (prev += 1));
    const favoriteCourseList = await getFavoritePlaces(
      currentPage + 1,
      divideCount,
      sortOption,
    );
    if (favoriteCourseList.status === 'failed') {
      setErrorText('북마크 데이터를 불러오는데 실패했습니다.');
      setIsLoading(false);
      return;
    }
    // setHasNext((prev) => (prev = favoriteCourseList.data));
    // setAllFavoriteCourseList((prev) => [...prev, ...favoriteCourseList.data]);
  };

  useIntersectionObserver(ref, getMoreBookMarks, hasNext);

  const onChangeSortOption = (option: string) => {
    setSortOption(option);
  };

  const getCourseList = async (
    pageNum: number,
    size: number,
    sortOrder: string,
  ) => {
    try {
      setIsLoading(true);
      const favoritePlaceList = await getFavoriteCourses(
        pageNum,
        size,
        sortOrder,
      );
      if (favoritePlaceList.status === 'failed') {
        // setAllFavoriteCourseList([]);
        setErrorText('북마크 데이터를 불러오는데 실패했습니다.');
        setIsLoading(false);
        return;
      }
      // setHasNext((prev) => (prev = favoritePlaceList.data.has_next));
      // setAllFavoriteCourseList([...favoritePlaceList.data.bookmarks]);
    } catch (error) {
      setErrorText('북마크 데이터를 불러오는데 실패했습니다.');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    setCurrentPage(1);
    scrollToTop();
    getCourseList(1, divideCount, sortOption);
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

      {allFavoriteCourseList.map(({ title, course_id, ...course }, idx) => (
        <div
          key={`${title}-${idx}`}
          className={`flex items-center border-b-2 ${
            idx === 0 && 'border-t-2'
          }`}
        >
          {/* <MyPagePlaceItem place_id={place_id} {...bookmark} /> */}
          {/** mypageCourseItem */}
          <LikeButton
            travelId={course_id}
            liked={true}
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
