'use server';

import { cookies } from 'next/headers';
import { revalidateTag } from 'next/cache';

import { getBaseUrl } from '@/app/utilActions';
import {
  TBackendRequestResult,
  backendErrorSchema,
  commentSchema,
  fetchableSchema,
  postResponseSchema,
} from '@/types/response';
import z from 'zod';

async function fetchComments(travelId: number, page: number, size: number) {
  const BASE_URL = await getBaseUrl();

  const endpoint = `${BASE_URL}/v1/comments/travels/${travelId}`;
  const queryParams = Object.entries({ page, size })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    next: { tags: [`comments/${travelId}`] },
  });

  return response.json();
}

export async function postComment(
  travelId: number,
  content: string,
): Promise<TBackendRequestResult<null>> {
  const session = cookies().get('SESSION')?.value;
  const BASE_URL = await getBaseUrl();

  const response = await fetch(`${BASE_URL}/v1/comments/travels/${travelId}`, {
    method: 'POST',
    body: JSON.stringify({
      content,
      parentId: travelId,
    }),
    headers: {
      Cookie: `SESSION=${session}`,
      'Content-Type': 'application/json',
    },
  });

  const json = await response.json();

  const error = backendErrorSchema.safeParse(json);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const result = postResponseSchema.safeParse(json);

  if (!result.success) {
    console.error('알 수 없는 에러입니다!');
    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }

  revalidateTag(`comments/${travelId}`);

  return { status: 'succeed', data: null };
}

const fetchableComments = fetchableSchema(commentSchema);

export async function getComments(
  travelId: number,
  page: number = 1,
  size: number = 5,
): Promise<TBackendRequestResult<z.infer<typeof fetchableComments>>> {
  const json = await fetchComments(travelId, page, size);

  const error = backendErrorSchema.safeParse(json);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const result = fetchableSchema(commentSchema).safeParse(json);

  if (!result.success) {
    console.error(`알 수 없는 에러입니다!`);
    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }

  return { status: 'succeed', data: result.data };
}
