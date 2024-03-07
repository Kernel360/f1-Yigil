'use server';

import { getBaseUrl } from '@/app/utilActions';
import { revalidateTag } from 'next/cache';

import { cookies } from 'next/headers';

export async function postLike(
  placeId: number,
  travelId: number,
  currentLike: boolean,
) {
  console.log(`postLike action: spots/${placeId}`);
  revalidateTag(`spots/${placeId}`);

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
    console.log('Yay');
    console.log(`spots/${placeId}`);
    revalidateTag(`spots/${placeId}`);
  }

  return JSON.stringify(await response.json());
}

// export async function toggleLike(
//   travelId: number,
//   currentLike: boolean,
// ): Promise<{ status: 'success' | 'failed'; message?: string }> {
//   const json = await postLike(travelId, currentLike);

//   const result = postResponseSchema.safeParse(json);

//   if (!result.success) {
//     return { status: 'failed', message: result.error.message };
//   }

//   return {
//     status: 'success',
//   };
// }
