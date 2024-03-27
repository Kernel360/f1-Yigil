'use server';

import { z } from 'zod';
import { cookies } from 'next/headers';

import { parseResult } from '@/utils';
import { getBaseUrl } from '@/app/utilActions';
import {
  TBackendRequestResult,
  backendErrorSchema,
  courseSchema,
  spotSchema,
  reportTypesSchema,
} from '@/types/response';

const spotsResponseSchema = z.object({
  spots: z.array(spotSchema),
  has_next: z.boolean(),
});

const coursesResponseSchema = z.object({
  courses: z.array(courseSchema),
  has_next: z.boolean(),
});

async function fetchSpots(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'created_at' | 'rate',
  sortOrder: 'desc' | 'asc',
) {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const endpoint = `${BASE_URL}/v1/spots/place/${placeId}`;
  const queryParams = Object.entries({ page, size, sortBy, sortOrder })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
    next: { tags: [`spots/${placeId}`] },
  });

  return await response.json();
}

export async function getSpots(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'created_at' | 'rate' = 'created_at',
  sortOrder: 'desc' | 'asc' = 'desc',
): Promise<TBackendRequestResult<z.infer<typeof spotsResponseSchema>>> {
  const json = await fetchSpots(placeId, page, size, sortBy, sortOrder);

  const error = backendErrorSchema.safeParse(json);

  if (error.success) {
    const { message, code } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const result = spotsResponseSchema.safeParse(json);

  if (!result.success) {
    console.error('알 수 없는 에러입니다!');
    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }

  return { status: 'succeed', data: result.data };
}

async function fetchCourses(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'created_at' | 'rate',
  sortOrder: 'desc' | 'asc',
) {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const endpoint = `${BASE_URL}/v1/courses/place/${placeId}`;
  const queryParams = Object.entries({ page, size, sortBy, sortOrder })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
    next: { tags: [`courses/${placeId}`] },
  });

  return await response.json();
}

export async function getCourses(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'created_at' | 'rate' = 'created_at',
  sortOrder: 'desc' | 'asc' = 'desc',
): Promise<TBackendRequestResult<z.infer<typeof coursesResponseSchema>>> {
  const json = await fetchCourses(placeId, page, size, sortBy, sortOrder);

  const error = backendErrorSchema.safeParse(json);

  if (error.success) {
    const { message, code } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const result = coursesResponseSchema.safeParse(json);

  if (!result.success) {
    console.error('알 수 없는 에러입니다!');
    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }

  return { status: 'succeed', data: result.data };
}

export async function getReportTypes() {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const response = await fetch(`${BASE_URL}/v1/reports/types`, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
  });

  const json = await response.json();

  const result = parseResult(reportTypesSchema, json);

  return result;
}
