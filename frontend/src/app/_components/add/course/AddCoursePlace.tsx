'use client';

import { useContext } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

import type { TChoosePlace } from '@/context/travel/schema';

const place: TChoosePlace = {
  name: '선택한 장소',
  address: '장소에 대한 도로명주소',
  mapImageUrl: '장소 위치 지도 이미지',
  coords: { lng: 35, lat: 125 },
};

export default function AddCoursePlace() {
  const [, dispatch] = useContext(CourseContext);

  return (
    <section className="grow">
      <button onClick={() => dispatch({ type: 'ADD_SPOT', payload: place })}>
        GOGOGO!!!
      </button>
    </section>
  );
}
