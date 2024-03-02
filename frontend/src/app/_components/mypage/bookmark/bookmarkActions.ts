'use server';
import { myPageBookmarkListSchema } from '@/types/myPageResponse';
import { requestWithCookie } from '../../api/httpRequest';

export const getMyPageBookmarks = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  const bookmarkList = await requestWithCookie('bookmarks')(
    `?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `createdAt&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }`,
  )()()();

  const parsedBookmarkList = myPageBookmarkListSchema.safeParse(bookmarkList);
  return parsedBookmarkList;
};

export const deleteMyPageBookmark = (placeId: number) => {
  return requestWithCookie('delete-bookmark')(`${placeId}`)('POST')()();
};

export const addMyPageBookmark = (placeId: number) => {
  return requestWithCookie('add-bookmark')(`${placeId}`)('POST')()();
};
