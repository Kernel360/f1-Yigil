'use client';

import { useContext, useState } from 'react';
import { StepContext } from '@/context/travel/step/StepContext';
import { CourseContext } from '@/context/travel/course/CourseContext';

import AddCoursePlace from './AddCoursePlace';
import AddCourseImages from './AddCourseImages';
import AddCourseReview from './AddCourseReviews';

export default function AddCourseData() {
  const [step] = useContext(StepContext);
  const [course] = useContext(CourseContext);

  const [index, setIndex] = useState(0);

  function selectIndex(nextIndex: number) {
    if (nextIndex < 0 || nextIndex >= course.spots.length) {
      return;
    }

    setIndex(nextIndex);
  }

  const { label, value } = step.data;

  switch (value) {
    case 0:
      return <>{label}</>;
    case 1:
      return <AddCoursePlace />;
    case 2:
      return <AddCourseImages index={index} selectIndex={selectIndex} />;
    case 3:
      return <AddCourseReview index={index} selectIndex={selectIndex} />;
    case 4:
      return <>{label}</>;
  }
}
