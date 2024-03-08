'use server';

import { z } from 'zod';
import { cookies } from 'next/headers';
import { requestWithCookie } from './httpRequest';
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';

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
  const { ENVIRONMENT, BASE_URL, DEV_BASE_URL } = process.env;

  const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

  const { id, nickname, profile_image_url, email, provider, accessToken } =
    data;

  return fetch(`${baseUrl}/v1/login`, {
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
