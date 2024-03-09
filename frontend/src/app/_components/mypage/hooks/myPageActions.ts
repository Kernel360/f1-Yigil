'use server';

import {
  myPageCourseListSchema,
  myPageSpotListSchema,
} from '@/types/myPageResponse';
import { revalidatePath } from 'next/cache';
import { requestWithCookie } from '../../api/httpRequest';
import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';

export const myPageSpotRequest = requestWithCookie('spots/my');
export const spotRequest = requestWithCookie('spots');
export const myPageCourseRequest = requestWithCookie('courses/my');
export const courseRequest = requestWithCookie('courses');
export const myPageFollowerRequest = requestWithCookie('follows/followers');
export const myPageFollowingRequest = requestWithCookie('follows/followings');

export const getMyPageSpots = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  selectOption: string = 'all',
) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(
    `${BASE_URL}/v1/spots/my?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `created_at&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }&selected=${selectOption}`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const spotList = await res.json();
  const parsedSpotList = myPageSpotListSchema.safeParse(spotList);
  return parsedSpotList;
};

export const deleteMySpot = async (spotId: number) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/spots/${spotId}`, {
    method: 'DELETE',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });

  if (res.ok) {
    revalidatePath('/mypage/my/travel/spot');
  }
};

export const getMyPageCourses = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  selectOption: string = 'all',
) => {
  const courseList = await myPageCourseRequest(
    `?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `created_at&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }&selected=${selectOption}`,
  )()()();
  const parsedCourseList = myPageCourseListSchema.safeParse(courseList);
  return parsedCourseList;
};

export const deleteMyCourse = async (courseId: number) => {
  const res = await courseRequest(`/${courseId}`)('DELETE')()();
  if (res) {
    revalidatePath('/mypage/my/travel/course');
    return res;
  }
};

export const changeOnPublicMyTravel = async (travel_id: number) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/travels/change-on-public`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(travel_id),
  });
  if (res.ok) {
    revalidatePath('/mypage/my/travel', 'layout');
  }
};

export const changeOnPrivateMyTravel = async (travel_id: number) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/travels/change-on-private`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(travel_id),
  });
  if (res.ok) {
    revalidatePath('/mypage/my/travel', 'layout');
  }
};

export const changeTravelsVisibility = async (
  travel_ids: number[],
  is_private: boolean,
) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const data = {
    travel_ids,
    is_private,
  };

  const res = await fetch(`${BASE_URL}/v1/travels/change-visibility`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });
  console.log(await res.json());
  if (res.ok) {
    revalidatePath('/mypage/my/travel', 'layout');
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
