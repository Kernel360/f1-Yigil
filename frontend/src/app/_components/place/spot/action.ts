'use server';

import { getBaseUrl } from '@/app/utilActions';
import {
  TBackendRequestResult,
  backendErrorSchema,
  postResponseSchema,
} from '@/types/response';
import { revalidateTag } from 'next/cache';

import { cookies } from 'next/headers';

export async function postLike(
  placeId: number,
  travelId: number,
  currentLike: boolean,
): Promise<TBackendRequestResult<null>> {
  const session = cookies().get('SESSION')?.value;
  const BASE_URL = await getBaseUrl();
  const endpoint = `${BASE_URL}/v1/${
    currentLike ? 'unlike' : 'like'
  }/${travelId}`;

  const response = await fetch(endpoint, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${session}`,
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

  revalidateTag(`spots/${placeId}`);
  revalidateTag(`courses/${placeId}`);

  return { status: 'succeed', data: null };
}
