'use client';
import { TMyPageFavoriteCourse } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import ToastMsg from '../../ui/toast/ToastMsg';
import LoadingIndicator from '../../LoadingIndicator';
import useIntersectionObserver from '../../hooks/useIntersectionObserver';
import { scrollToTop } from '@/utils';

import LikeButton from '../../course/LikeButton';
import { getFavoriteCourses } from './FavoriteActions';
import CourseItem from '../CourseItem';

const sortOptions = [
  { label: '이름순', value: 'title' },
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
];

export default function FavoriteCourseList({
  favoriteCourseList,
  has_next,
}: {
  favoriteCourseList: TMyPageFavoriteCourse['contents'];
  has_next: boolean;
}) {
  const [sortOption, setSortOption] = useState('title');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;
  const [allFavoriteCourseList, setAllFavoriteCourseList] =
    useState(favoriteCourseList);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [hasNext, setHasNext] = useState(has_next);

  const ref = useRef<HTMLDivElement>(null);

  const getMoreFavoriteCourses = async () => {
    const favoriteCourseList = await getFavoriteCourses(
      currentPage + 1,
      divideCount,
      sortOption,
    );
    if (favoriteCourseList.status === 'failed') {
      setHasNext(false);
      setErrorText('코스 데이터를 불러오는데 실패했습니다.');
      setIsLoading(false);
      setTimeout(() => {
        setErrorText('');
      }, 1000);
      return;
    }
    setHasNext((prev) => (prev = favoriteCourseList.data.has_next));
    setAllFavoriteCourseList((prev) => [
      ...prev,
      ...favoriteCourseList.data.contents,
    ]);
    setCurrentPage((prev) => (prev += 1));
  };

  useIntersectionObserver(ref, getMoreFavoriteCourses, hasNext);

  const onChangeSortOption = (option: string) => {
    setSortOption(option);
  };

  const getCourseList = async (
    pageNum: number,
    size: number,
    sortOrder: string,
  ) => {
    setIsLoading(true);
    const favoriteCourseList = await getFavoriteCourses(
      pageNum,
      size,
      sortOrder,
    );
    if (favoriteCourseList.status === 'failed') {
      setAllFavoriteCourseList([]);
      setErrorText('코스 데이터를 불러오는데 실패했습니다.');
      setIsLoading(false);
      setTimeout(() => {
        setErrorText('');
      }, 1000);
      return;
    }
    setHasNext((prev) => (prev = favoriteCourseList.data.has_next));
    setAllFavoriteCourseList(favoriteCourseList.data.contents);
    setIsLoading(false);
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
          defaultValue="이름순"
        />
      </div>

      {allFavoriteCourseList.map(({ title, course_id, ...course }, idx) => (
        <div
          key={`${title}-${idx}`}
          className={`flex items-center border-b-2 py-6 ${
            idx === 0 && 'border-t-2'
          }`}
        >
          <div className="flex-[5]">
            <CourseItem
              course_id={course_id}
              {...course}
              title={title}
              is_private={false}
            />
          </div>
          <div className="relative flex-[1] flex justify-end">
            <LikeButton
              travelId={course_id}
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
      {errorText && <ToastMsg title={errorText} timer={1000} />}
    </div>
  );
}
