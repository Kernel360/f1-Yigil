'use server';

import { getBaseUrl } from '@/app/utilActions';
import { revalidateTag } from 'next/cache';

import { cookies } from 'next/headers';

export async function postLike(
  placeId: number,
  travelId: number,
  currentLike: boolean,
) {
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

  if (response.ok) {
    revalidateTag(`spots/${placeId}`);
  }

  return JSON.stringify(await response.json());
}
