'use client';

import { useContext, useState } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

import { InfoTitle } from '../../common';
import CourseImageHandler from '../../../images/CourseImageHandler';
import SelectSpot from '../SelectSpot';

export default function AddCourseImages() {
  const [state] = useContext(CourseContext);
  const [index, setIndex] = useState(0);

  function selectIndex(nextIndex: number) {
    setIndex(nextIndex);
  }

  return (
    <section className="flex flex-col justify-center grow">
      <InfoTitle label="사진" additionalLabel="을 업로드하세요." />
      <SelectSpot key="images" index={index} selectIndex={selectIndex} />
      {index !== -1 && (
        <div className="p-4 flex justify-between items-center">
          <span className="pr-8 text-lg text-gray-400 shrink-0">이름</span>
          <span className="text-2xl font-medium">
            {state.spots[index].place.name}
          </span>
        </div>
      )}
      <CourseImageHandler order={index} />
    </section>
  );
}
