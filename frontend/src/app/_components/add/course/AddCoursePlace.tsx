'use client';

import AddCourseNewPlace from './AddCourseNewPlace';

export default function AddCoursePlace({
  method,
}: {
  method: 'with-new' | 'without-new';
}) {
  if (method === 'with-new') {
    return <AddCourseNewPlace />;
  }

  return <>select 컴온~</>;
}
