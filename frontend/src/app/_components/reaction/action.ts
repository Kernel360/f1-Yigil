'use server';

import z from 'zod';
import { cookies } from 'next/headers';
import { revalidateTag } from 'next/cache';

import { parseResult } from '@/utils';
import { getBaseUrl } from '@/app/utilActions';
import {
  backendErrorSchema,
  commentSchema,
  fetchableSchema,
  postResponseSchema,
} from '@/types/response';

import type { TBackendRequestResult } from '@/types/response';

async function fetchComments(travelId: number, page: number, size: number) {
  const BASE_URL = await getBaseUrl();

  const endpoint = `${BASE_URL}/v1/comments/travels/${travelId}`;
  const queryParams = Object.entries({ page, size })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    next: { tags: [`comments/${travelId}`] },
  });

  return await response.json();
}

export async function postComment(travelId: number, content: string) {
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

  const result = parseResult(postResponseSchema, json);

  if (result.status === 'succeed') {
    revalidateTag(`comments/${travelId}`);
  }

  return result;
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

  const fetchableComments = fetchableSchema(commentSchema);

  const result = parseResult(fetchableComments, json);

  return result;
}

export async function modifyComment(
  travelId: number,
  commentId: number,
  draft: string,
) {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const response = await fetch(`${BASE_URL}/v1/comments/${commentId}`, {
    method: 'PUT',
    headers: {
      Cookie: `SESSION=${session}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ content: draft }),
  });

  const json = await response.json();

  const result = parseResult(postResponseSchema, json);

  if (result.status === 'succeed') {
    revalidateTag(`comments/${travelId}`);
  }

  return result;
}

export async function deleteComment(travelId: number, commentId: number) {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const response = await fetch(`${BASE_URL}/v1/comments/${commentId}`, {
    method: 'DELETE',
    headers: {
      Cookie: `SESSION=${session}`,
      'Content-Type': 'application/json',
    },
  });

  const json = await response.json();

  const result = parseResult(postResponseSchema, json);

  if (result.status === 'succeed') {
    revalidateTag(`comments/${travelId}`);
  }

  return result;
}
