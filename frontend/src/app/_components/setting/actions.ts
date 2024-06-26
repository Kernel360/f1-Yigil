'use server';

import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';
import { dataUrlToBlob, parseResult } from '@/utils';
import { revalidatePath } from 'next/cache';
import { TBackendRequestResult, postResponseSchema } from '@/types/response';
import z from 'zod';

export async function checkIsExistNickname(nickname: string) {
  const BASE_URL = await getBaseUrl();
  const res = await fetch(`${BASE_URL}/v1/members/nickname_duplicate_check`, {
    method: 'POST',
    body: JSON.stringify(nickname),
    headers: { 'Content-Type': 'application/json' },
  });
  return res.json();
}

export async function patchFavoriteRegion(
  ids: number[],
): Promise<TBackendRequestResult<z.infer<typeof postResponseSchema>>> {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const formData = new FormData();
  if (!ids.length) formData.set('favoriteRegionIds', '');
  else ids.forEach((id) => formData.append('favoriteRegionIds', id.toString()));

  const res = await fetch(`${BASE_URL}/v1/members`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
    body: formData,
  });
  const result = await res.json();

  const parsed = parseResult(postResponseSchema, result);
  if (res.ok) revalidatePath('/setting', 'layout');
  return parsed;
}

export async function patchUserInfo(infoData: {
  [key: string]: any;
}): Promise<TBackendRequestResult<z.infer<typeof postResponseSchema>>> {
  const formData = new FormData();
  for (let key in infoData) {
    if (key === 'profile_image_url') {
      if (!infoData[key]) {
        formData.append('isProfileEmpty', 'true');
      } else {
        if (infoData[key] !== '변경 없음')
          formData.append(
            'profileImageFile',
            new File(
              [dataUrlToBlob(infoData[key])],
              `${infoData.nickname} 프로필 이미지.png`,
              {
                type: 'image/png',
              },
            ),
          );
      }
      continue;
    }
    formData.append(key, infoData[key]);
  }

  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/members`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
    body: formData,
  });
  const result = await res.json();

  const parsed = parseResult(postResponseSchema, result);
  if (res.ok) revalidatePath('/setting', 'layout');
  return parsed;
}
