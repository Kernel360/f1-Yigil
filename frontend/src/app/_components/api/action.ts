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
