'use server';

import { cookies } from 'next/headers';
import { httpRequest } from '../../api/httpRequest';

export const myPageSpotRequest = httpRequest('members/spots');
export const myPageCourseRequest = httpRequest('member/courses');
export const myPageFollowerRequest = httpRequest('member/followers');
export const myPageFollowingRequest = httpRequest('member/followings');

const cookie = cookies().get('SESSION')?.value;
export const authenticateUser = async () => {
  const res = await httpRequest('members')()()({
    'Content-Type': 'application/json',
    Cookie: `SESSION=${cookie}`,
  })();
  return res;
};

export const getMyPageSpots = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy: string = 'rate',
) => {
  const res = await myPageSpotRequest(
    `?page=${pageNo}&size=${size}&sortOrder=${sortBy ? 'desc' : sortOrder}${
      sortBy ? '&sortBy=rate' : ''
    }`,
  )()({
    Cookie: `SESSION=${cookie}`,
  })();
  return res;
};

export const deleteMyPageSpot = (spotId: number) => {
  return myPageSpotRequest(`${spotId}`)('DELETE')({
    Cookie: `SESSION=${cookie}`,
  })();
};

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
  )()({ Cookie: `SESSION=${cookie}` })();
  return res;
};
export const getMyPageFollwings = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  const res = await myPageFollowingRequest(
    `?page=${pageNo}&size=${size}&sortOrder=${sortBy ? 'desc' : sortOrder}${
      sortBy ? '&sortBy=rate' : ''
    }`,
  )()({ Cookie: `SESSION=${cookie}` })();
  return res;
};

export const getSpotList = async () => {
  const res = await httpRequest('spots/places')(
    `/1?page=1&size=5&sortBy=rate&sortOrder=desc`,
  )()()();
  return res;
};
