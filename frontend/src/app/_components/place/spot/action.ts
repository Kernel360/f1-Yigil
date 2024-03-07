'use server';

import { getBaseUrl } from '@/app/utilActions';
import { postResponseSchema } from '@/types/response';
import { cookies } from 'next/headers';

async function postLike(travelId: number, currentLike: boolean) {
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

  return await response.json();
}

export async function toggleLike(travelId: number, currentLike: boolean) {
  const json = await postLike(travelId, currentLike);

  const result = postResponseSchema.safeParse(json);

  return result;
}
