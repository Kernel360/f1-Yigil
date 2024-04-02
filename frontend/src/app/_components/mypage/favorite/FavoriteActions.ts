'use server';

import { getBaseUrl } from '@/app/utilActions';
import {
  myPageFavoriteCourseResponse,
  myPageFavoriteSpotSchema,
} from '@/types/myPageResponse';
import { TBackendRequestResult, backendErrorSchema } from '@/types/response';
import { parseResult } from '@/utils';
import { cookies } from 'next/headers';
import z from 'zod';

export const getFavoriteSpots = async (
  page: number = 1,
  size: number = 5,
  sortOption: string = 'place_name',
): Promise<TBackendRequestResult<z.infer<typeof myPageFavoriteSpotSchema>>> => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(
    `${BASE_URL}/v1/spots/my/favorite?page=${page}&size=${size}${
      sortOption === 'place_name'
        ? '&sortBy=place_name&sortOrder=asc'
        : `&sortBy=created_at&sortOrder=${sortOption}`
    }`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const result = await res.json();
  const error = backendErrorSchema.safeParse(result);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }
  const parsed = parseResult(myPageFavoriteSpotSchema, result);
  return parsed;
};

export const getFavoriteCourses = async (
  page: number = 1,
  size: number = 5,
  sortOption: string = 'title',
): Promise<
  TBackendRequestResult<z.infer<typeof myPageFavoriteCourseResponse>>
> => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(
    `${BASE_URL}/v1/courses/my/favorite?page=${page}&size=${size}${
      sortOption === 'title'
        ? '&sortBy=title&sortOrder=asc'
        : `&sortBy=created_at&sortOption=${sortOption}`
    }`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const result = await res.json();
  const error = backendErrorSchema.safeParse(result);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const parsed = parseResult(myPageFavoriteCourseResponse, result);
  return parsed;
};
