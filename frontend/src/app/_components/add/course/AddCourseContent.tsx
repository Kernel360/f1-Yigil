'use client';

import { useContext } from 'react';

import { StepContext } from '@/context/travel/step/StepContext';
import { CourseContext } from '@/context/travel/course/CourseContext';

export default function AddCourseContent() {
  const [step, dispatchStep] = useContext(StepContext);
  const [spot, dispatchCourse] = useContext(CourseContext);

  return (
    <section>
      {step.data.value === 1 ? <>{step.data.label}</> : <>{step.data.label}</>}
    </section>
  );
}
