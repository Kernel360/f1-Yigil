'use client';

import { useContext } from 'react';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { CourseContext } from '@/context/travel/course/CourseContext';

import XMarkIcon from '/public/icons/x-mark.svg';

export default function AddCourseSelectPlaceNavigation() {
  const [, dispatchAddTravel] = useContext(AddTravelMapContext);
  const [course, dispatchCourse] = useContext(CourseContext);

  function onClose() {
    dispatchAddTravel({ type: 'CLOSE_RESULT' });
    dispatchAddTravel({ type: 'CLOSE_MAP' });
  }

  return (
    <nav className="mx-4 relative flex justify-between items-center">
      <span className="absolute left-0 right-0 text-center text-xl text-semibold text-gray-900">
        장소 선택
      </span>
      <button
        className="relative"
        onClick={() => {
          dispatchCourse({ type: 'INIT_COURSE' });
          onClose();
        }}
      >
        <XMarkIcon className="w-6 h-6 stroke-gray-500" />
      </button>
      {course.spots.length >= 2 && (
        <button className="relative text-lg text-main" onClick={onClose}>
          완료
        </button>
      )}
    </nav>
  );
}
