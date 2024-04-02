'use server';

import { getBaseUrl } from '@/app/utilActions';
import {
  myPageFollowerResponseSchema,
  myPageFollowResponseSchema,
} from '@/types/myPageResponse';
import {
  backendErrorSchema,
  postResponseSchema,
  TBackendRequestResult,
} from '@/types/response';
import { parseResult } from '@/utils';
import { cookies } from 'next/headers';
import z from 'zod';

export const getFollowList = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'id',
  action: 'followers' | 'followings',
): Promise<
  TBackendRequestResult<
    | z.infer<typeof myPageFollowResponseSchema>
    | z.infer<typeof myPageFollowerResponseSchema>
  >
> => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;

  const res = await fetch(
    `${BASE_URL}/v1/follows/${action}?page=${pageNo}&size=${size}&
      ${
        sortOrder === 'id'
          ? 'sortBy=id&sortOrder=asc'
          : `sortBy=created_at&sortOrder=${sortOrder}`
      }
`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
      next: {
        tags: ['following'],
      },
    },
  );
  const followList = await res.json();
  const error = backendErrorSchema.safeParse(followList);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const parsedFollowList =
    action === 'followings'
      ? parseResult(myPageFollowResponseSchema, followList)
      : parseResult(myPageFollowerResponseSchema, followList);
  return parsedFollowList;
};

export const postFollow = async (
  memberId: number,
  isFollowed: boolean,
): Promise<TBackendRequestResult<z.infer<typeof postResponseSchema>>> => {
  const action = isFollowed ? 'unfollow' : 'follow';
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;

  const res = await fetch(`${BASE_URL}/v1/follows/${action}/${memberId}`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });

  const result = await res.json();

  const error = backendErrorSchema.safeParse(result);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const parsed = parseResult(postResponseSchema, result);
  return parsed;
};
