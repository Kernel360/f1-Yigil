'use server';

import { cookies } from 'next/headers';
import { revalidateTag } from 'next/cache';

import { getBaseUrl } from '@/app/utilActions';
import { backendErrorSchema, postResponseSchema } from '@/types/response';

interface TBackendRequestFailed {
  status: 'failed';
  message: string;
  code?: number;
}
interface TBackendRequestSucceed<T> {
  status: 'succeed';
  data: T;
}
type TBackendRequestResult<T> =
  | TBackendRequestFailed
  | TBackendRequestSucceed<T>;

export async function postLikeCourse(
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
    const { message, code } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const result = postResponseSchema.safeParse(json);

  if (!result.success) {
    console.error(`${response.status} - 알 수 없는 에러입니다!`);
    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }

  revalidateTag(`courses/${travelId}`);

  return { status: 'succeed', data: null };
}
