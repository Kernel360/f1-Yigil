'use server';

import { revalidatePath } from 'next/cache';
import { requestWithCookie } from '../../api/httpRequest';

export const myPageSpotRequest = requestWithCookie('spots/my');
export const spotRequest = requestWithCookie('spots');
export const myPageCourseRequest = requestWithCookie('courses/my');
export const courseRequest = requestWithCookie('courses');
export const myPageFollowerRequest = requestWithCookie('follows/followers');
export const myPageFollowingRequest = requestWithCookie('follows/followings');

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
    `?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `createdAt&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }&selected=${selectOption}`,
  )()()();
};

export const deleteMySpot = async (spotId: number) => {
  const res = await spotRequest(`/${spotId}`)('DELETE')()();
  if (res) {
    revalidatePath('/mypage/my/travel/spot');
    return res;
  }
};

export const getMyPageCourses = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  selectOption: string = 'all',
) => {
  return myPageCourseRequest(
    `?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `createdAt&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }&selected=${selectOption}`,
  )()()();
};

export const deleteMyCourse = async (courseId: number) => {
  const res = await courseRequest(`/${courseId}`)('DELETE')()();
  if (res) {
    revalidatePath('/mypage/my/travel/course');
    return res;
  }
};

export const changeOnPublicMyTravel = async (travel_id: number) => {
  const res = await requestWithCookie('travel/change-on-public')()(
    'POST',
    travel_id,
  )()();
  if (res) {
    revalidatePath('/mypage/my/travel', 'layout');
    return res;
  }
};

export const changeOnPriavateMyTravel = async (travel_id: number) => {
  const res = await requestWithCookie('travel/change-on-private')()(
    'POST',
    travel_id,
  )()();
  if (res) {
    revalidatePath('/mypage/my/travel', 'layout');
    return res;
  }
};

export const changeTravelsVisibility = async (
  travel_ids: number[],
  is_private: boolean,
) => {
  const res = await requestWithCookie('travel/change-visibility')()('POST', {
    travel_ids,
    is_private,
  })()();
  if (res) {
    revalidatePath('/mypage/my/travel', 'layout');
    return res;
  }
};

export const getMyPageFollwers = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  const res = await myPageFollowerRequest(
    `?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate' ? `id&sortOrder=${sortOrder}` : `rate&sortOrder=desc`
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

export const getMyPageBookmarks = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  return requestWithCookie('bookmarks')(
    `?page=${pageNo}&size=${size}&sortOrder=${
      sortOrder !== 'rate' ? sortOrder : `sortOrder=desc&sortBy=rate`
    }`,
  )()()();
};

export const deleteMyPageBookmark = (placeId: number) => {
  return requestWithCookie('delete-bookmark')(`${placeId}`)()()();
};

export const addMyPageBookmark = (placeId: number) => {
  return requestWithCookie('add-bookmark')(`${placeId}`)()()();
};
