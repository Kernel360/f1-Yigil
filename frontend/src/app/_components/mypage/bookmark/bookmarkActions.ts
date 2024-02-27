'use server';
import { requestWithCookie } from '../../api/httpRequest';

export const myPageBookmarkRequest = requestWithCookie('members/bookmarks');

export const getMyPageBookmarks = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  return myPageBookmarkRequest(
    `?page=${pageNo}&size=${size}&sortOrder=${
      sortOrder !== 'rate' ? sortOrder : `sortOrder=desc&sortBy=rate`
    }`,
  )()()();
};

// TODO: url 변경 여부 확인해야 함.
export const deleteMyPageBookmark = (placeId: number) => {
  return myPageBookmarkRequest(`/${placeId}`)()()('삭제 실패');
};

export const addMyPageBookmark = (placeId: number) => {};
