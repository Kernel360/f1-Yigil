'use server';

import { getBaseUrl } from '@/app/utilActions';
import {
  myPageFollowerResponseSchema,
  myPageFollowResponseSchema,
  TMyPageFollowerResponse,
  TMyPageFollowResponse,
} from '@/types/myPageResponse';
import { backendErrorSchema, postResponseSchema } from '@/types/response';
import { cookies } from 'next/headers';

export const getFollowingList = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'id',
): Promise<
  | { status: 'failed'; message: string }
  | { status: 'succeed'; data: TMyPageFollowResponse }
> => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;
  try {
    const res = await fetch(
      `${BASE_URL}/v1/follows/followings?page=${pageNo}&size=${size}&
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
    const followingList = await res.json();
    const error = backendErrorSchema.safeParse(followingList);

    if (error.success) {
      console.error(`${error.data.code} - ${error.data.message}`);
      return {
        status: 'failed',
        message: `${error.data.code} - ${error.data.message}`,
      };
    }

    const parsedFollowList =
      myPageFollowResponseSchema.safeParse(followingList);
    if (!parsedFollowList.success) {
      console.error(parsedFollowList.error.message);
      return { status: 'failed', message: '알 수 없는 에러입니다.' };
    }
    return JSON.parse(JSON.stringify(parsedFollowList));
  } catch (error) {
    if (error instanceof Error) {
      console.error(error.message);
      return { status: 'failed', message: error.message };
    }
    return { status: 'failed', message: '알 수 없는 에러입니다.' };
  }
};

export const getFollowerList = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'id',
): Promise<
  | { status: 'failed'; message: string }
  | { status: 'succeed'; data: TMyPageFollowerResponse }
> => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;

  try {
    const res = await fetch(
      `${BASE_URL}/v1/follows/followers?page=${pageNo}&size=${size}&${
        sortOrder === 'id'
          ? 'sortBy=id&sortOrder=asc'
          : `sortBy=created_at&sortOrder=${sortOrder}`
      }`,
      {
        headers: {
          Cookie: `SESSION=${cookie}`,
        },
        next: {
          tags: ['follower'],
        },
      },
    );
    const followerList = await res.json();
    const error = backendErrorSchema.safeParse(followerList);

    if (error.success) {
      console.error(`${error.data.code} - ${error.data.message}`);
      return {
        status: 'failed',
        message: `${error.data.code} - ${error.data.message}`,
      };
    }

    const parsedFollowerList =
      myPageFollowerResponseSchema.safeParse(followerList);
    if (!parsedFollowerList.success) {
      console.error(parsedFollowerList.error.message);
      return { status: 'failed', message: '알 수 없는 에러입니다.' };
    }
    return JSON.parse(JSON.stringify(parsedFollowerList));
  } catch (error) {
    if (error instanceof Error) {
      console.error(error.message);
      return { status: 'failed', message: error.message };
    }
    return { status: 'failed', message: '알 수 없는 에러입니다.' };
  }
};

export const postFollow = async (memberId: number, isFollowed: boolean) => {
  const action = isFollowed ? 'unfollow' : 'follow';
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;
  try {
    const res = await fetch(`${BASE_URL}/v1/follows/${action}/${memberId}`, {
      method: 'POST',
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    });

    const result = await res.json();
    const error = backendErrorSchema.safeParse(result);

    if (error.success) {
      console.error(`${error.data.code} - ${error.data.message}`);
      return { status: 'failed', message: error.data.message };
    }
    console.log(result);
    const parsedResult = postResponseSchema.safeParse(result);

    if (!parsedResult.success) {
      return { status: 'failed', message: 'zod 검증 에러입니다' };
    }

    return { status: 'succeed', message: '성공' };
  } catch (error) {
    if (error instanceof Error) {
      console.error(error.message);
      return { status: 'failed', message: error.message };
    }
    return { status: 'failed', message: '알 수 없는 에러입니다.' };
  }
};
