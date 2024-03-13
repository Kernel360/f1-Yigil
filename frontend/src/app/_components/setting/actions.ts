'use server';

import { getBaseUrl } from '@/app/utilActions';
import { TMyInfo, myInfoSchema } from '@/types/response';
import { cookies } from 'next/headers';
import { dataUrlToBlob } from '@/utils';
import { revalidatePath, revalidateTag } from 'next/cache';
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

export async function patchFavoriteRegion(ids: number[]) {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const formData = new FormData();
  ids.forEach((id) => formData.append('favoriteRegionIds', id.toString()));

  const res = await fetch(`${BASE_URL}/v1/members`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
    body: formData,
  });
  if (res.ok) {
    // revalidateTag('user');
    return res.json();
  }
}

export async function patchUserInfo(infoData: { [key: string]: any }) {
  const formData = new FormData();
  for (let key in infoData) {
    if (key === 'profile_image_url') {
      if (!infoData[key]) {
        formData.append('profileImageUrl', '');
      } else {
        formData.append(
          'profileImageUrl',
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

  if (res.ok) {
    // revalidatePath('/settings', 'layout');
    return res.json();
  }
}
