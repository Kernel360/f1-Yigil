'use client';
import { TMyPageFollow } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import { getFollowingList } from '../hooks/followActions';
import MyPageFollowingItem from './MyPageFollowingItem';
import useIntersectionObserver from '../../hooks/useIntersectionObserver';
import LoadingIndicator from '../../LoadingIndicator';

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

export default function MyPageFollowingList({
  followingList,
}: {
  followingList: TMyPageFollow[];
}) {
  const [selectOption, setSelectOption] = useState('id');
  const [currentPage, setCurrentPage] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [hasNext, setHasNext] = useState(false);
  const [allFollowingList, setAllFollowingList] =
    useState<TMyPageFollow[]>(followingList);
  const ref = useRef(null);

  const getMoreFollowings = async () => {
    setCurrentPage((prev) => (prev += 1));
    const followingList = await getFollowingList(
      currentPage + 1,
      5,
      selectOption,
    );
    if (followingList.status === 'failed') {
      setAllFollowingList([]);
      setErrorText(followingList.message);
      return;
    }
    setAllFollowingList(followingList.data.content);
    setHasNext(followingList.data.has_next);
  };

  useIntersectionObserver(ref, getMoreFollowings, hasNext);

  const onChangeSelectOption = (option: string | number) => {
    if (typeof option === 'number') return;
    setSelectOption(option);
    setCurrentPage(1);
  };

  const getFollowingLists = async (
    pageNo: number,
    size: number,
    selectOption: string,
  ) => {
    try {
      setErrorText('');
      setIsLoading(true);
      const followingList = await getFollowingList(pageNo, size, selectOption);
      if (followingList.status === 'failed') {
        setAllFollowingList([]);
        setErrorText(followingList.message);
        return;
      }
      setAllFollowingList(followingList.data.content);
      setHasNext(followingList.data.has_next);
    } catch (error) {
      if (error instanceof Error) {
        console.error(error.message);
      }
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    getFollowingLists(currentPage, 5, selectOption);
  }, [currentPage, selectOption]);
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
      {allFollowingList.map((follow, idx) => (
        <MyPageFollowingItem key={follow.member_id} {...follow} idx={idx} />
      ))}
      <div className="flex justify-center my-8" ref={ref}>
        {hasNext &&
          (isLoading ? (
            <LoadingIndicator loadingText="데이터 로딩중" />
          ) : (
            <button className="py-1 px-8 bg-gray-200 rounded-lg">더보기</button>
          ))}
      </div>
    </div>
  );
}
