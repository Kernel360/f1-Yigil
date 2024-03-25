'use client';
import { TMyPageFollower } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import useIntersectionObserver from '../../hooks/useIntersectionObserver';
import Select from '../../ui/select/Select';
import { getFollowerList } from '../hooks/followActions';
import MyPageFollowerItem from './MyPageFollowerItem';
import LoadingIndicator from '../../LoadingIndicator';
import ToastMsg from '../../ui/toast/ToastMsg';

const selectList = [
  {
    label: '이름순',
    value: 'id',
  },
  {
    label: '최신순',
    value: 'desc',
  },
  {
    label: '오래된순',
    value: 'asc',
  },
];

export default function MyPageFollowerList({
  followerList,
}: {
  followerList: TMyPageFollower[];
}) {
  const [selectOption, setSelectOption] = useState('id');
  const [currentPage, setCurrentPage] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [hasNext, setHasNext] = useState(false);

  const [allFollowerList, setAllFollowerList] =
    useState<TMyPageFollower[]>(followerList);
  const ref = useRef<HTMLDivElement | null>(null);

  const getMoreFollowers = async () => {
    setCurrentPage((prev) => (prev += 1));
    const followerList = await getFollowerList(
      currentPage + 1,
      5,
      selectOption,
    );
    if (followerList.status === 'failed') {
      setAllFollowerList([]);
      setErrorText(followerList.message);
      return;
    }
    setAllFollowerList((prev) => [...prev, ...followerList.data.content]);
    setHasNext(followerList.data.has_next);
  };

  useIntersectionObserver(ref, getMoreFollowers, hasNext);

  const onChangeSelectOption = (option: string) => {
    setSelectOption(option);
    setCurrentPage(1);
  };

  const getFollowerLists = async (
    pageNo: number,
    size: number,
    selectOption: string,
  ) => {
    setIsLoading(true);
    const followerList = await getFollowerList(pageNo, size, selectOption);
    if (followerList.status === 'failed') {
      setAllFollowerList([]);
      setErrorText(followerList.message);
      setTimeout(() => {
        setErrorText('');
      }, 1000);
      setIsLoading(false);
      return;
    }

    setAllFollowerList(followerList.data.content);
    setHasNext(followerList.data.has_next);
    setIsLoading(false);
  };

  useEffect(() => {
    getFollowerLists(1, 5, selectOption);
  }, [selectOption]);
  return (
    <div className="px-4">
      <div className="flex justify-end mt-6 mb-10">
        <Select
          list={selectList}
          onChangeSelectOption={onChangeSelectOption}
          selectOption={selectOption}
          defaultValue="이름순"
        />
      </div>

      {allFollowerList.map((follow) => (
        <MyPageFollowerItem key={follow.member_id} {...follow} />
      ))}
      <div className="flex justify-center my-8" ref={ref}>
        {hasNext &&
          (isLoading ? (
            <LoadingIndicator loadingText="데이터 로딩중" />
          ) : (
            <button className="py-1 px-8 bg-gray-200 rounded-lg">더보기</button>
          ))}
      </div>
      {errorText && <ToastMsg title={errorText} timer={1000} id={Date.now()} />}
    </div>
  );
}
