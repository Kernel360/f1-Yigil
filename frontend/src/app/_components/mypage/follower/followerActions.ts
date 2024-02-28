'use server';
import { requestWithCookie } from '../../api/httpRequest';

export const myPageFollowerRequest = requestWithCookie('members/followers');

export const getMyPageFollwers = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  const res = await myPageFollowerRequest(
    `?page=${pageNo}&size=${size}&sortOrder=${sortBy ? 'desc' : sortOrder}${
      sortBy ? '&sortBy=rate' : ''
    }`,
  )()()();
  return res;
};
