'use client';

import { useContext } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

export default function AddCourseConfirm() {
  const [] = useContext(CourseContext);

  return <section className="flex flex-col grow"></section>;
}
