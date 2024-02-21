'use server';

import { cookies } from 'next/headers';
import { requestWithCookie } from '../../api/httpRequest';

export const myPageSpotRequest = requestWithCookie('members/spots');
export const myPageCourseRequest = requestWithCookie('member/courses');
export const myPageFollowerRequest = requestWithCookie('member/followers');
export const myPageFollowingRequest = requestWithCookie('member/followings');

const cookie = cookies().get('SESSION')?.value;
export const authenticateUser = async () => {
  const res = await requestWithCookie('members')()()()();
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
  )()()();
  return res;
};

export const deleteMyPageSpot = (spotId: number) => {
  return myPageSpotRequest(`${spotId}`)('DELETE')()();
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
  )()()();
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
  )()()();
  return res;
};

export const getSpotList = async () => {
  const res = await requestWithCookie('spots/places')(
    `/1?page=1&size=5&sortBy=rate&sortOrder=desc`,
  )()()();
  return res;
};
