'use server';

import { cookies } from 'next/headers';
import { revalidateTag } from 'next/cache';

import { getBaseUrl } from '@/app/utilActions';
import {
  TComment,
  commentSchema,
  fetchableSchema,
  postResponseSchema,
  spotSchema,
} from '@/types/response';

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

async function postComment(travelId: number, content: string) {
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
    },
  });

  return response.json();
}

export async function getComments(
  travelId: number,
  page: number = 1,
  size: number = 5,
) {
  const json = await fetchComments(travelId, page, size);

  const result = fetchableSchema(commentSchema).safeParse(json);

  return result;
}

export async function writeComment(travelId: number, content: string) {
  const json = await postComment(travelId, content);

  const result = postResponseSchema.safeParse(json);

  if (result.success) {
    revalidateTag(`comments/${travelId}`);
  }

  return result;
}
