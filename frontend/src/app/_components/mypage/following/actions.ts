'use server';

import { myPageFollowListSchema } from '@/types/myPageResponse';
import { cookies } from 'next/headers';

const BASE_URL =
  process.env.ENVIRONMENT === 'production'
    ? process.env.BASE_URL
    : process.env.NEXT_PUBLIC_API_MOCKING !== 'enabled'
    ? process.env.DEV_BASE_URL
    : typeof window === 'undefined'
    ? 'http://localhost:8080/api'
    : 'http://localhost:3000/api';

export const getFollowingList = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'asc',
) => {
  const res = await fetch(
    `${BASE_URL}/v1/follows/followings?page=${pageNo}&size=${size}&sortBy=id&sortOrder=${sortOrder}`,
    {
      headers: {
        Cookie: `SESSION=${cookies().get('SESSION')?.value}`,
      },
      next: { tags: ['following'] },
    },
  );

  const followList = await res.json();
  const parsedFollowList = myPageFollowListSchema.safeParse(followList);
  return parsedFollowList;
};
