'use client';
import React, { useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import MyPageSpotItem from './MyPageSpotItem';

export interface TMyPageSpotType {
  post_id: number;
  travel_id: number;
  image_url: string;
  rating: number;
  post_date: string;
  title: string;
  description: string;
  isSecret: boolean;
}

export default function MyPageSpotList({
  placeList,
}: {
  placeList: TMyPageSpotType[];
}) {
  const [checkedList, setCheckedList] = useState<TMyPageSpotType['post_id'][]>(
    [],
  );

  const filterCheckedList = (id: number) => {
    if (!checkedList.includes(id)) setCheckedList([...checkedList, id]);
    else {
      const filteredList = checkedList.filter((checkedId) => checkedId !== id);
      setCheckedList(filteredList);
    }
  };

  return (
    <>
      <div className="flex justify-end">
        <select name="" id="" className="p-2 ">
          <option value="">최신순</option>
          <option value="">오래된순</option>
          <option value="">평점순</option>
        </select>
      </div>
      {placeList.map(({ post_id, ...data }, idx) => (
        <MyPageSpotItem
          idx={idx}
          key={post_id}
          post_id={post_id}
          {...data}
          checkedList={checkedList}
          filterCheckedList={filterCheckedList}
        />
      ))}
    </>
  );
}
