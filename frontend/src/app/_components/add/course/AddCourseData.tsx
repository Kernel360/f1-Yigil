'use client';

import { StepContext } from '@/context/travel/step/StepContext';
import { useContext } from 'react';

export default function AddCourseData() {
  const [step] = useContext(StepContext);

  const { label, value } = step.data;

  switch (value) {
    case 0:
      return <>{label}</>;
    case 1:
      return <>{label}</>;
    case 2:
      return <>{label}</>;
    case 3:
      return <>{label}</>;
    case 4:
      return <>{label}</>;
  }
}
