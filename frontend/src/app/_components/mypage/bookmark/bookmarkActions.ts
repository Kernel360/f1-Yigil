'use server';
import { requestWithCookie } from '../../api/httpRequest';

export const getMyPageBookmarks = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  return requestWithCookie('bookmarks')(
    `?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `createdAt&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }`,
  )()()();
};

export const deleteMyPageBookmark = (placeId: number) => {
  return requestWithCookie('delete-bookmark')(`${placeId}`)('POST')()();
};

export const addMyPageBookmark = (placeId: number) => {
  return requestWithCookie('add-bookmark')(`${placeId}`)('POST')()();
};
