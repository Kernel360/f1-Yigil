'use server';

import { getBaseUrl } from '@/app/utilActions';
import { TMyInfo } from '@/types/response';
import { cookies } from 'next/headers';
import { TModifyUser } from './ModifyUser';
import { dataUrlToBlob } from '@/utils';

export async function checkIsExistNickname(nickname: string) {
  const BASE_URL = await getBaseUrl();
  const res = await fetch(`${BASE_URL}/v1/members/nickname_duplicate_check`, {
    method: 'POST',
    body: JSON.stringify(nickname),
  });
  console.log(await res.json());
}

export async function patchUserInfo(infoData: TModifyUser) {
  const formData = new FormData();
  infoData.favorite_regions.forEach((region) =>
    formData.append('favoriteRegionIds', region.id.toString()),
  );
  formData.append('nickname', infoData.nickname);
  formData.append('age', infoData.age);
  formData.append('gender', infoData.gender);
  formData.append(
    'profileImageUrl',
    new File(
      [dataUrlToBlob(infoData.profile_image_url)],
      `${infoData.nickname} 프로필 이미지.webp`,
      {
        type: 'image/webp',
      },
    ),
  );
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(`${BASE_URL}/v1/members`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
    body: formData,
  });
  return res.json();
}
