'use server';

import { z } from 'zod';

import { cookies } from 'next/headers';
import { getBaseUrl } from '@/app/utilActions';
import { myPageSpotListSchema } from '@/types/myPageResponse';
import { backendErrorSchema } from '@/types/response';

type TMyPageSpotList = z.infer<typeof myPageSpotListSchema>;

async function fetchMySpots(pageNo: number, size: number, sortOrder: string) {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const endpoint = `${BASE_URL}/v1/spots/my`;
  const queryParams = Object.entries({
    page: pageNo,
    size,
    sortBy:
      sortOrder !== 'rate'
        ? `created_at&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`,
  })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });

  return await response.json();
}

export async function getMySpots(
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
): Promise<
  | { status: 'failed'; message: string }
  | { status: 'succeed'; data: TMyPageSpotList }
> {
  try {
    const json = await fetchMySpots(pageNo, size, sortOrder);

    const error = backendErrorSchema.safeParse(json);

    if (error.success) {
      console.error(`${error.data.code} - ${error.data.message}`);
      throw new Error(`${error.data.code} - ${error.data.message}`);
    }

    const result = myPageSpotListSchema.safeParse(json);

    if (!result.success) {
      console.log(result.error.message);
      throw new Error('알 수 없는 에러입니다!');
    }

    return { status: 'succeed', data: result.data };
  } catch (error) {
    if (error instanceof Error) {
      console.error(error.message);
      return { status: 'failed', message: error.message };
    }

    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }
}
