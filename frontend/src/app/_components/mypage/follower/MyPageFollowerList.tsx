'use client';
import { TMyPageFollow } from '@/types/myPageResponse';
import React, { useEffect, useRef, useState } from 'react';
import Select from '../../ui/select/Select';
import { getFollowerList } from '../hooks/followActions';
import MyPageFollowerItem from './MyPageFollowerItem';

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
  followerList: TMyPageFollow[];
}) {
  const [selectOption, setSelectOption] = useState('id');
  const [currentPage, setCurrentPage] = useState(1);
  const [hasNext, setHasNext] = useState(false);
  const [allFollowerList, setAllFollowerList] =
    useState<TMyPageFollow[]>(followerList);
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
    const followerList = await getFollowerList(pageNo, size, selectOption);
    if (!followerList.success) {
      setAllFollowerList([]);
      return;
    }
    setAllFollowerList(followerList.data.content);
    setHasNext(followerList.data.has_next);
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

      {allFollowerList.map((follow, idx) => (
        <MyPageFollowerItem key={follow.member_id} {...follow} idx={idx} />
      ))}
    </div>
  );
}
