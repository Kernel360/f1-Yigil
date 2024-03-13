'use client';

import { useContext } from 'react';
import { StepContext } from '@/context/travel/step/StepContext';

import AddCoursePlace from './AddCoursePlace';
import AddCourseImages from './AddCourseImages';
import AddCourseReview from './AddCourseReviews';

export default function AddCourseData() {
  const [step] = useContext(StepContext);

  const { label, value } = step.data;

  switch (value) {
    case 0:
      return <>{label}</>;
    case 1:
      return <AddCoursePlace />;
    case 2:
      return <AddCourseImages />;
    case 3:
      return <AddCourseReview />;
    case 4:
      return <>{label}</>;
  }
}
