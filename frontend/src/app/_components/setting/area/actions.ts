'use server';

import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';

export async function getAllArea() {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/regions/select`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });
  return res.json();
}
