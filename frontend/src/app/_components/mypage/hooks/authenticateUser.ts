'use server';

import { cookies } from 'next/headers';
import { getBaseUrl } from '@/app/utilActions';

export const authenticateUser = async () => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(`${BASE_URL}/v1/members`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
    // next: { tags: ['user'] },
  });
  return res.json();
};
