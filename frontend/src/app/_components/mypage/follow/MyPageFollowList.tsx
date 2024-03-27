'use client';
import { TMyPageFollow, TMyPageFollower } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import useIntersectionObserver from '../../hooks/useIntersectionObserver';
import Select from '../../ui/select/Select';
import { getFollowList } from '../hooks/followActions';
import MyPageFollowItem from './MyPageFollowItem';
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

export default function MyPageFollowList({
  followList,
  action,
}: {
  followList: TMyPageFollower[] | TMyPageFollow[];
  action: 'followers' | 'followings';
}) {
  const [selectOption, setSelectOption] = useState('id');
  const [currentPage, setCurrentPage] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [hasNext, setHasNext] = useState(false);

  const [allFollowList, setAllFollowList] = useState<
    TMyPageFollower[] | TMyPageFollow[]
  >(followList);
  const ref = useRef<HTMLDivElement | null>(null);

  const getMoreFollows = async () => {
    const followList = await getFollowList(
      currentPage + 1,
      5,
      selectOption,
      action,
    );
    if (followList.status === 'failed') {
      setAllFollowList([]);
      setErrorText(followList.message);
      return;
    }
    setAllFollowList((prev) => [...prev, ...followList.data.content]);
    setHasNext(followList.data.has_next);
    setCurrentPage((prev) => (prev += 1));
  };

  useIntersectionObserver(ref, getMoreFollows, hasNext);

  const onChangeSelectOption = (option: string) => {
    setSelectOption(option);
    setCurrentPage(1);
  };

  const getFollowLists = async (
    pageNo: number,
    size: number,
    selectOption: string,
  ) => {
    setIsLoading(true);
    const followerList = await getFollowList(
      pageNo,
      size,
      selectOption,
      action,
    );
    if (followerList.status === 'failed') {
      setAllFollowList([]);
      setErrorText(followerList.message);
      setTimeout(() => {
        setErrorText('');
      }, 1000);
      setIsLoading(false);
      return;
    }

    setAllFollowList(followerList.data.content);
    setHasNext(followerList.data.has_next);
    setIsLoading(false);
  };

  useEffect(() => {
    getFollowLists(1, 5, selectOption);
  }, [selectOption]);
  return (
    <div className="px-4">
      <div className="flex justify-end mt-6 mb-10">
        <Select
          list={selectList}
          selectStyle="p-2"
          onChangeSelectOption={onChangeSelectOption}
          selectOption={selectOption}
          defaultValue="이름순"
        />
      </div>

      {allFollowList.map((follow) => (
        <MyPageFollowItem key={follow.member_id} {...follow} action={action} />
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
