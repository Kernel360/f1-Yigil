'use client';
import { TMyPageFollowing } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import { getFollowingList } from './actions';
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
  followingList: TMyPageFollowing[];
}) {
  const [selectOption, setSelectOption] = useState('id');
  const [currentPage, setCurrentPage] = useState(1);
  const [hasNext, setHasNext] = useState(false);
  const [allFollowingList, setAllFollowingList] =
    useState<TMyPageFollowing[]>(followingList);
  const ref = useRef(null);
  console.log(followingList);
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
    console.log('caalee');
    const followingList = await getFollowingList(pageNo, size, selectOption);
    console.log('cli', followingList);
    // if (!followingList.success) setAllFollowingList([]);
    // setAllFollowingList(followingList.data.content);
    // setHasNext(followingList.data.content.has_next);
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
      <div className="grid grid-cols-3">
        <MyPageFollowingItem />
      </div>
    </div>
  );
}
