'use client';

import { useContext } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

export default function AddCoursePlace({ method }: { method: 1 | 2 }) {
  const [, dispatch] = useContext(CourseContext);

  if (method === 1) {
    return <>지도 컴온~</>;
  }

  return <>select 컴온~</>;
}
