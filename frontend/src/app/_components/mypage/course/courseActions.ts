'use server';

import { requestWithCookie } from '@/app/_components/api/httpRequest';

export const myPageCourseRequest = requestWithCookie('members/courses');

export const getMyPageCourses = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  selectOption: string = 'all',
) => {
  return myPageCourseRequest(
    `?page=${pageNo}&size=${size}&sortOrder=${
      sortOrder !== 'rate' ? sortOrder : `sortOrder=desc&sortBy=rate`
    }&selected=${selectOption}`,
  )()()();
};
