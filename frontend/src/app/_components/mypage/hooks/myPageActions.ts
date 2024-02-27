'use server';

import { requestWithCookie } from '../../api/httpRequest';

export const myPageSpotRequest = requestWithCookie('members/spots');
export const myPageCourseRequest = requestWithCookie('members/courses');
export const myPageFollowerRequest = requestWithCookie('members/followers');
export const myPageFollowingRequest = requestWithCookie('members/followings');

export const authenticateUser = async () => {
  return requestWithCookie('members')()()()();
};

export const getMyPageSpots = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  selectOption: string = 'all',
) => {
  return myPageSpotRequest(
    `?page=${pageNo}&size=${size}&sortOrder=${
      sortOrder !== 'rate' ? sortOrder : `sortOrder=desc&sortBy=rate`
    }&selected=${selectOption}`,
  )()()();
};

export const deleteMyPageSpot = (spotId: number) => {
  return myPageSpotRequest(`${spotId}`)('DELETE')()();
};

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
