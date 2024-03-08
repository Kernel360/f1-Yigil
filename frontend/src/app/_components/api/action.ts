'use server';

import { z } from 'zod';
import { cookies } from 'next/headers';
import { requestWithCookie } from './httpRequest';
import { getBaseUrl } from '@/app/utilActions';

export async function logout() {
  const response = await requestWithCookie('logout')()()()(
    '로그아웃에 실패했습니다!',
  );

  const result = logoutSuccessResponse.safeParse(response);

  if (!result.success) {
    console.log(result.error.message);
    console.error(result.error.message);
    return;
  }

  cookies().delete('SESSION');
}

const logoutSuccessResponse = z.object({
  message: z.string(),
});

export async function backendLoginRequest(data: {
  id: string;
  nickname: string;
  profile_image_url: string;
  email: string;
  provider: string;
  accessToken: string;
}) {
  const BASE_URL = await getBaseUrl();

  const { id, nickname, profile_image_url, email, provider, accessToken } =
    data;

  return fetch(`${BASE_URL}/v1/login`, {
    method: 'POST',
    body: JSON.stringify({
      id,
      nickname,
      profile_image_url,
      email,
      provider,
    }),
    headers: {
      Authorization: `Bearer ${accessToken}`,
      'Content-Type': 'application/json',
    },
  });
}
