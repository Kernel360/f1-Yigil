'use client';
import React, { useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';

import { TMyPageSpot } from '../spot/MyPageSpotList';
import MyPageCourseItem from './MyPageCourseItem';

export interface TMyPageCourse {
  course_id: number;
  travel_id: number;
  title: string;
  image_url: string;
  isSecret: boolean;
  post_date: string;
  rating: number;
  spots: number;
}

export default function MyPageCourseList({
  placeList,
}: {
  placeList: TMyPageCourse[];
}) {
  const [selected, setSelected] = useState('');

  const onChangeSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelected(e.target.value);
  };

  const [checkedList, setCheckedList] = useState<TMyPageSpot['postId'][]>([]);

  const filterCheckedList = (id: number) => {
    if (!checkedList.includes(id)) setCheckedList([...checkedList, id]);
    else {
      const filteredList = checkedList.filter((checkedId) => checkedId !== id);
      setCheckedList(filteredList);
    }
  };
  // 필터링 처리 방법 백엔드 요청 | 프론트에서 처리
  return (
    <>
      <div className="flex justify-end">
        <select
          className="py-2 mx-4"
          name="place-filter"
          id="place-select"
          onChange={onChangeSelect}
        >
          <option value="최신순">최신순</option>
          <option value="거리순">거리순</option>
        </select>
      </div>
      <div className="relative">
        {/* <FloatingActionButton popOverData={myPageCoursePopOverData} /> */}
      </div>
      {!!placeList.length &&
        placeList.map(({ course_id, ...data }, idx) => (
          <MyPageCourseItem
            key={course_id}
            idx={idx}
            course_id={course_id}
            {...data}
            checkedList={checkedList}
            filterCheckedList={filterCheckedList}
          />
        ))}
    </>
  );
}
