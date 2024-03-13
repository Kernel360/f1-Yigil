'use client';

import { useContext, useState } from 'react';
import CourseImageHandler from '../../images/CourseImageHandler';
import { InfoTitle } from '../common';
import { CourseContext } from '@/context/travel/course/CourseContext';

export default function AddCourseImages({
  index,
  selectIndex,
}: {
  index: number;
  selectIndex: (nextOrder: number) => void;
}) {
  const [state] = useContext(CourseContext);

  return (
    <section className="flex flex-col grow">
      <InfoTitle label="사진" additionalLabel="을 업로드하세요." />
      {/* 특정 장소 선택 UI */}
      <div className="flex flex-col">
        <button onClick={() => selectIndex(index - 1)}>Previous</button>
        <button onClick={() => selectIndex(index + 1)}>Next</button>
        <span>{`${index + 1} / ${state.spots.length}`}</span>
      </div>
      <div className="px-4 pb-4 flex justify-between items-center">
        <span className="text-gray-400">이름</span>
        <span className="text-xl">{state.spots[index].place.name}</span>
      </div>
      <CourseImageHandler order={index} />
    </section>
  );
}
