'use client';

import { useContext } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

export default function AddCoursePlace({
  method,
}: {
  method: 'with-new' | 'without-new';
}) {
  const [, dispatch] = useContext(CourseContext);

  if (method === 'with-new') {
    return <>지도 컴온~</>;
  }

  return <>select 컴온~</>;
}
