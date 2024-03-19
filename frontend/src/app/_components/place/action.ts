'use server';

import { z } from 'zod';

import { getBaseUrl } from '@/app/utilActions';
import { revalidatePath, revalidateTag } from 'next/cache';
import { cookies } from 'next/headers';

import { backendErrorSchema, placeSchema } from '@/types/response';

import type { TPlace } from '@/types/response';

/**
 * Client Component에서 Server Action을 실행할 때, 직렬화할 수 없는 복잡한 자료구조를 반환값으로 돌려줄 수 없음
 *
 * 따라서 ZodSafeReturnType을 돌려줄 수 없어 Client에서 검증을 수행함
 *
 * @link https://react.dev/reference/react/use-server#serializable-parameters-and-return-values
 */
export async function postBookmark(placeId: number, bookmarked: boolean) {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const action = bookmarked ? 'delete' : 'add';

  const response = await fetch(`${BASE_URL}/v1/${action}-bookmark/${placeId}`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${session}`,
      'Content-Type': 'application/json',
    },
  });

  if (response.ok) {
    revalidateTag(`placeDetail/${placeId}`);
  }
  return await response.json();
}

const manyPlaceSchema = z.object({
  places: z.array(placeSchema),
});

export async function getRecommendedPlaces(): Promise<
  | { status: 'failed'; message: string; code: number }
  | { status: 'succeed'; data: TPlace[] }
> {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const response = await fetch(`${BASE_URL}/v1/places/popular-demographics`, {
    method: 'GET',
    headers: {
      Cookie: `SESSION=${session}`,
    },
    next: { tags: ['recommended'] },
  });

  const json = await response.json();

  const error = backendErrorSchema.safeParse(json);

  if (error.success) {
    const { code, message } = error.data;
    return { status: 'failed', code, message };
  }

  const result = manyPlaceSchema.safeParse(json);

  if (!result.success) {
    console.log(result.error.message);
    return { status: 'failed', message: '알 수 없는 에러입니다!', code: 500 };
  }

  return { status: 'succeed', data: result.data.places };
}
