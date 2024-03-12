'use server';
import { myPageBookmarkListSchema } from '@/types/myPageResponse';
import { requestWithCookie } from '../../api/httpRequest';
import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';
import { revalidatePath } from 'next/cache';

export const getMyPageBookmarks = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(
    `${BASE_URL}/v1/bookmarks?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `created_at&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
        'Content-Type': 'application/json',
      },
    },
  );
  const result = await res.json();
  const bookmarkList = myPageBookmarkListSchema.safeParse(result);

  return bookmarkList;
};

export const deleteMyPageBookmark = (placeId: number) => {
  return requestWithCookie('delete-bookmark')(`${placeId}`)('POST')()();
};

export const addMyPageBookmark = (placeId: number) => {
  return requestWithCookie('add-bookmark')(`${placeId}`)('POST')()();
};
