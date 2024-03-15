'use server';
import { myPageBookmarkListSchema } from '@/types/myPageResponse';
import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';

export const getMyPageBookmarks = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(
    `${BASE_URL}/v1/bookmarks?page=${pageNo}&size=${size}&sortBy=${
      sortOrder === 'desc' || sortOrder === 'asc'
        ? `created_at&sortOrder=${sortOrder}`
        : sortOrder === 'place_name'
        ? 'place_name&sortOrder=asc'
        : `${sortOrder}&sortOrder=desc`
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
