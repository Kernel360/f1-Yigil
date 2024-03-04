'use client';
import { TMyPageFollow } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import { getFollowingList } from '../hooks/followActions';
import MyPageFollowingItem from './MyPageFollowingItem';

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
  const [currentPage, setCurrentPage] = useState(0);
  const [hasNext, setHasNext] = useState(false);
  const [allFollowingList, setAllFollowingList] =
    useState<TMyPageFollow[]>(followingList);
  const ref = useRef(null);

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
    const followingList = await getFollowingList(pageNo, size, selectOption);
    if (!followingList.success) {
      setAllFollowingList([]);
      return;
    }
    setAllFollowingList(followingList.data.content);
    setHasNext(followingList.data.has_next);
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
    </div>
  );
}
