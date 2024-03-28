'use server';

import { getBaseUrl } from '@/app/utilActions';
import { postResponseSchema } from '@/types/response';
import { parseResult } from '@/utils';
import { cookies } from 'next/headers';

export const getFavoritePlaces = async (
  page: number = 1,
  size: number = 5,
  sortOption: string = 'place_name',
) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(
    `${BASE_URL}/v1/spots/my/favorite?page=${page}&size=${size}${
      sortOption === 'name'
        ? '&sortBy=place_name&sortOrder=asc'
        : `&sortBy=created_at&sortOption=${sortOption}`
    }`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const result = await res.json();
  const parsed = parseResult(postResponseSchema, result);

  return parsed;
};

export const getFavoriteCourses = async (
  page: number = 1,
  size: number = 5,
  sortOption: string = 'title',
) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(
    `${BASE_URL}/v1/spots/my/favorite?page=${page}&size=${size}${
      sortOption === 'name'
        ? '&sortBy=place_name&sortOrder=asc'
        : `&sortBy=created_at&sortOption=${sortOption}`
    }`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const result = await res.json();
  const parsed = parseResult(postResponseSchema, result);

  return parsed;
};
