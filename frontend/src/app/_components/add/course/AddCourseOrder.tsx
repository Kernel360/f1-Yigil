import { CourseContext } from '@/context/travel/course/CourseContext';
import { useContext } from 'react';

export default function AddCourseOrder() {
  const [state] = useContext(CourseContext);

  return <>순서 정하기~</>;
}
