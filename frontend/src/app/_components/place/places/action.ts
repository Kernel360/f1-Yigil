'use server';

import { z } from 'zod';

import { parseResult } from '@/utils';
import { getBaseUrl } from '@/app/utilActions';
import { revalidateTag } from 'next/cache';
import { cookies } from 'next/headers';

import {
  backendErrorSchema,
  placeSchema,
  postResponseSchema,
} from '@/types/response';

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
    revalidateTag('popular');
    revalidateTag('region');
    revalidateTag('recommended');
    revalidateTag(`placeDetail/${placeId}`);
  }
  return await response.json();
}

const manyPlaceSchema = z.object({
  places: z.array(placeSchema),
});

type TPlaceResult =
  | { status: 'failed'; message: string; code: number }
  | { status: 'succeed'; data: TPlace[] };

async function getEndpoint(
  type: 'popular' | 'region' | 'recommended',
  more?: boolean,
  id?: number,
) {
  const BASE_URL = await getBaseUrl();

  const placeEndpoint = `${BASE_URL}/v1/places`;

  switch (type) {
    case 'popular': {
      return `${placeEndpoint}/popular${more ? '/more' : ''}`;
    }
    case 'region': {
      return `${placeEndpoint}/region/${id}${more ? '/more' : ''}`;
    }
    case 'recommended': {
      return `${placeEndpoint}/popular-demographics${more ? '-more' : ''}`;
    }
  }
}

async function fetchPlaces(
  type: 'popular' | 'region' | 'recommended',
  more?: boolean,
  id?: number,
) {
  const endpoint = await getEndpoint(type, more, id);
  const session = cookies().get('SESSION')?.value;

  const response = await fetch(endpoint, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
    next: { tags: [type] },
  });

  return await response.json();
}

export async function getPlaces(
  type: 'popular' | 'region' | 'recommended',
  more?: 'more',
  id?: number,
): Promise<TPlaceResult> {
  const json = await fetchPlaces(type, more === 'more', id);

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

export async function toggleFollowTravelOwner(
  ownerId: number,
  following: boolean,
) {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const action = following ? 'unfollow' : 'follow';

  const response = await fetch(`${BASE_URL}/v1/follows/${action}/${ownerId}`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${session}`,
    },
  });

  const json = await response.json();

  const result = parseResult(postResponseSchema, json);

  if (result.status === 'succeed') {
    revalidateTag('following');
  }

  return result;
}
